package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.ExerciseListItem
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.domain.useCases.RemoveExerciseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import moe.tlaster.precompose.viewmodel.viewModelScope
import kotlin.time.Duration.Companion.seconds

class ThisTrainingViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider,
    private val summaryUseCaseProvider: SummaryUseCaseProvider,
    private val removeTrainingExerciseUseCase: RemoveExerciseUseCase
) : moe.tlaster.precompose.viewmodel.ViewModel() {

    private val _state = MutableStateFlow(ThisTrainingState())
    val state = _state.onStart {
        loadTrainingAndExercises()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        ThisTrainingState()
    )

    fun onEvent(event: ThisTrainingEvent) {
        when (event) {
            is ThisTrainingEvent.OnExerciseClick -> {
                state.value.ongoingTraining?.let { training ->
                    viewModelScope.launch(Dispatchers.IO) {
                        insertExerciseHistory(
                            exercise = event.exercise,
                            training = training
                        ) { exerciseId ->
                            viewModelScope.launch(Dispatchers.Main) {
                                event.navigateToExercise(exerciseId)
                            }
                        }
                    }
                }
            }

            ThisTrainingEvent.EndTraining -> {
                state.value.ongoingTraining?.let { ongoingTraining ->
                    endOngoingTraining(ongoingTraining)
                }
            }

            is ThisTrainingEvent.OnCollapsedEvent -> {
                val updatedList = state.value.exerciseLocalsWithHeaders?.map {
                    if (it == event.item) {
                        (it as ExerciseListItem.ExerciseItem).copy(isOptionsReveled = false)
                    } else it
                }

                _state.update {
                    it.copy(
                        exerciseLocalsWithHeaders = updatedList
                    )
                }
            }

            is ThisTrainingEvent.OnExtendedEvent -> {
                val updatedList = state.value.exerciseLocalsWithHeaders?.map {
                    if (it == event.item) {
                        (it as ExerciseListItem.ExerciseItem).copy(isOptionsReveled = true)
                    } else it
                }
                _state.update {
                    it.copy(
                        exerciseLocalsWithHeaders = updatedList
                    )
                }
            }

            is ThisTrainingEvent.OnRemoveExercise -> {
                state.value.ongoingTraining?.let { ongoingTraining ->
                    viewModelScope.launch {
                        removeExercise(
                            ongoingTraining,
                            state.value.exercisesLocal,
                            event
                        )
                    }
                }
            }
        }
    }

    private suspend fun removeExercise(
        ongoingTraining: Training,
        localExercises: List<ExerciseLocal>,
        event: ThisTrainingEvent.OnRemoveExercise
    ) = withContext(Dispatchers.IO) {
        removeTrainingExerciseUseCase.removeExercise(
            ongoingTraining = ongoingTraining,
            exercises = localExercises.map { it.name },
            selectedExercise = event.exerciseName
        )
        loadTrainingAndExercises()
    }

    private suspend fun loadLastSameTrainingData(ongoingTrainingTemplateId: Long) {
        val lastTraining = trainingUseCaseProvider.getLastSameHistoryTrainingUseCase()
            .invoke(trainingTemplateId = ongoingTrainingTemplateId)
            .first()
        _state.update {
            it.copy(
                lastTraining = lastTraining
            )
        }
    }

    private fun loadTrainingAndExercises() = viewModelScope.launch {
        val ongoingTraining = trainingUseCaseProvider.getOngoingTrainingUseCase().invoke().first()
        ongoingTraining?.let { trainingNotNull ->
            _state.update {
                it.copy(
                    ongoingTraining = trainingNotNull,
                )
            }
            val exercises = exerciseUseCaseProvider.getLocalExercisesByNamesUseCase()
                .invoke(trainingNotNull.exercises).first()
            _state.update {
                it.copy(
                    exercisesLocal = exercises,
                    exerciseLocalsWithHeaders = getExerciseListWithHeaders(exercises.sortedBy { item -> item.group })
                )
            }
            loadLastSameTrainingData(ongoingTrainingTemplateId = trainingNotNull.trainingTemplateId)
        }
        if (ongoingTraining != null) {
            startTimer(ongoingTraining)
        }
    }

    private suspend fun startTimer(training: Training) {
        countTrainingTime(training).collect { time ->
            _state.update {
                it.copy(timerState = state.value.timerState.copy(trainingTime = time))
            }
        }
    }

    private fun endOngoingTraining(ongoingTraining: Training) =
        viewModelScope.launch(Dispatchers.IO) {
            ongoingTraining.id?.let { ongoingTrainingId ->
                if (ongoingTraining.totalLiftedWeight == 0.0) {
                    trainingUseCaseProvider.getDeleteTrainingHistoryRecordUseCase()
                        .invoke(ongoingTrainingId)
                    return@let
                }
                trainingUseCaseProvider.getUpdateTrainingHistoryStatusUseCase()
                    .invoke(trainingId = ongoingTrainingId)
                updateSummaries()
            }
        }

    private suspend fun updateSummaries() {
        state.value.ongoingTraining?.let { ongoingTraining ->
            summaryUseCaseProvider.getUpdateSummariesUseCase().invoke(ongoingTraining)
        }
    }

    private suspend fun insertExerciseHistory(
        exercise: ExerciseLocal,
        training: Training,
        navigateToExercise: (Long) -> Unit
    ) {
        val (exerciseId, exerciseExists) = isExerciseExists(training, exercise)
        if (exerciseExists == 0L) {
            exerciseUseCaseProvider.getInsertExerciseHistoryUseCase().invoke(
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

    private suspend fun isExerciseExists(
        training: Training,
        exercise: ExerciseLocal
    ): Pair<Long, Long> {
        val exerciseId = exercise.id!!
        val exerciseExists =
            exerciseUseCaseProvider.getCountExerciseInHistoryUseCase().invoke(
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