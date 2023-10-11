package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.seconds

class ThisTrainingViewModel(
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    private val exerciseDatasource: IExerciseDatasource,
    private val exerciseHistoryDatasource: IExerciseHistoryDatasource
) : ViewModel() {

    private val _training = MutableStateFlow<Training?>(null)
    private val _exercises = MutableStateFlow<List<ExerciseLocal>?>(null)
    private val _state = MutableStateFlow(ThisTrainingState())
    val state = combine(
        _state,
        _training,
        _exercises
    ) { state, training, exercises ->
        val newState =
            if (training != state.training) {
                state.copy(
                    training = training,
                    exerciseLocals = exercises
                )
            } else state
        newState
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        ThisTrainingState()
    )

    init {
        loadTrainingAndExercises()
    }

    private fun loadTrainingAndExercises() = viewModelScope.launch(Dispatchers.IO) {
        trainingHistoryDataSource.getOngoingTraining().collect { training ->
            training?.let { trainingNotNull ->
                _training.value = trainingNotNull
                val exercises =
                    exerciseDatasource.getExercisesByNames(trainingNotNull.exercises).first()
                _exercises.value = exercises
                startTimer(trainingNotNull)
            }
        }
    }

    private fun startTimer(training: Training) = viewModelScope.launch(Dispatchers.Main) {
        countTrainingTime(training).collect { time ->
            _state.update {
                it.copy(
                    trainingTime = time
                )
            }
        }
    }

    fun onEvent(event: ThisTrainingEvent) {
        when (event) {
            is ThisTrainingEvent.OnExerciseClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    insertExerciseHistory(exercise = event.exercise) { exerciseId ->
                        viewModelScope.launch(Dispatchers.Main) {
                            event.navigateToExercise(exerciseId)
                        }
                    }
                }
            }
        }
    }

    private suspend fun insertExerciseHistory(
        exercise: ExerciseLocal,
        navigateToExercise: (Long) -> Unit
    ) {
        val (trainingId, exerciseId, exerciseExists) = isExerciseExists(exercise)
        if (exerciseExists == 0L) {
            exerciseHistoryDatasource.insertExerciseHistory(
                Exercise(
                    id = null,
                    name = exercise.name,
                    group = exercise.group,
                    trainingHistoryId = trainingId,
                    exerciseTemplateId = exerciseId,
                )
            )
        }
        navigateToExercise.invoke(exerciseId)
    }

    private suspend fun isExerciseExists(exercise: ExerciseLocal): Triple<Long, Long, Long> {
        val trainingId = state.value.training!!.id!!
        val exerciseId = exercise.id!!
        val exerciseExists =
            exerciseHistoryDatasource.countExerciseInHistory(
                trainingHistoryId = trainingId,
                exerciseTemplateId = exerciseId
            ).first()
        return Triple(trainingId, exerciseId, exerciseExists)
    }

    private fun countTrainingTime(training: Training) = flow {
        while (training.status == "ONGOING") {
            val durationMillis = Clock.System.now().toEpochMilliseconds()
                .minus(training.startTime?.seconds?.inWholeSeconds ?: 0)
            val totalSeconds = durationMillis / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            kotlinx.coroutines.delay(1000L)
            emit("Training time: ${hours}h:${minutes}m:${seconds}s")
        }
    }.flowOn(Dispatchers.Default)
}