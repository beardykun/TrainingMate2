package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.ExerciseListItem
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
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
    private val exerciseHistoryDatasource: IExerciseHistoryDatasource,
    private val summaryDatasource: ISummaryDatasource
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
            if (training != state.ongoingTraining) {
                state.copy(
                    ongoingTraining = training,
                    exerciseLocals = getExerciseListWithHeaders(exercises?.sortedBy { it.group }
                        ?: emptyList())
                )
            } else state
        newState
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000L),
        ThisTrainingState()
    )

    init {
        loadTrainingAndExercises()
    }

    private suspend fun loadLastTrainingData(ongoingTrainingId: Long) {
        val lastTraining =
            trainingHistoryDataSource.getLastTraining(trainingTemplateId = ongoingTrainingId)
                .first()
        _state.update {
            it.copy(
                lastTraining = lastTraining
            )
        }
    }

    private fun loadTrainingAndExercises() = viewModelScope.launch(Dispatchers.IO) {
        val ongoingTraining = trainingHistoryDataSource.getOngoingTraining().first()
        ongoingTraining?.let { trainingNotNull ->
            _training.value = trainingNotNull
            val exercises =
                exerciseDatasource.getExercisesByNames(trainingNotNull.exercises).first()
            _exercises.value = exercises
            startTimer(trainingNotNull)
            loadLastTrainingData(ongoingTrainingId = trainingNotNull.trainingTemplateId)
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

            ThisTrainingEvent.EndTraining -> {
                endLastTraining()
            }
        }
    }

    private fun endLastTraining() = viewModelScope.launch(Dispatchers.IO) {
        state.value.ongoingTraining?.id?.let { ongoingTrainingId ->
            if (state.value.ongoingTraining?.totalWeightLifted == 0.0) {
                trainingHistoryDataSource.deleteTrainingRecord(ongoingTrainingId)
                return@let
            }
            trainingHistoryDataSource.updateStatus(trainingId = ongoingTrainingId)
            updateSummaries()
        }
    }

    private suspend fun updateSummaries() {
        state.value.ongoingTraining?.let { ongoingTraining ->
            val duration = Utils.trainingLengthToMin(ongoingTraining)
            summaryDatasource.updateDuration(additionalDuration = duration)
            summaryDatasource.updateTotalWeight(
                additionalWeight = ongoingTraining.totalWeightLifted,
                numExercises = ongoingTraining.doneExercises.size,
                numSets = ongoingTraining.totalSets,
                numReps = ongoingTraining.totalReps
            )
        }
    }

    private suspend fun insertExerciseHistory(
        exercise: ExerciseLocal,
        navigateToExercise: (Long) -> Unit
    ) {
        state.value.ongoingTraining?.let { training ->
            val (exerciseId, exerciseExists) = isExerciseExists(training, exercise)
            if (exerciseExists == 0L) {
                exerciseHistoryDatasource.insertExerciseHistory(
                    Exercise(
                        id = null,
                        name = exercise.name,
                        group = exercise.group,
                        date = Utils.formatEpochMillisToDate(
                            Clock.System.now().toEpochMilliseconds()
                        ),
                        trainingHistoryId = training.id ?: 0,
                        trainingTemplateId = training.trainingTemplateId,
                        exerciseTemplateId = exerciseId,
                    )
                )
            }
            navigateToExercise.invoke(exerciseId)
        }
    }

    private suspend fun isExerciseExists(
        training: Training,
        exercise: ExerciseLocal
    ): Pair<Long, Long> {
        val exerciseId = exercise.id!!
        val exerciseExists =
            exerciseHistoryDatasource.countExerciseInHistory(
                trainingHistoryId = training.id ?: 0,
                exerciseTemplateId = exerciseId
            ).first()
        return Pair(exerciseId, exerciseExists)
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
            emit("${hours}h:${minutes}m:${seconds}s")
        }
    }.flowOn(Dispatchers.Default)

    private fun getExerciseListWithHeaders(exercises: List<ExerciseLocal>): List<ExerciseListItem> {
        val items = mutableListOf<ExerciseListItem>()
        var lastGroup = ""

        for (exercise in exercises) {
            if (exercise.group != lastGroup) {
                items.add(ExerciseListItem.Header(exercise.group))
                lastGroup = exercise.group
            }
            items.add(ExerciseListItem.ExerciseItem(exercise))
        }

        return items
    }
}