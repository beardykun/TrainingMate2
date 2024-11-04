package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddExercisesViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider
) : ViewModel() {

    private val _training = MutableStateFlow<Training?>(null)


    private val _state = MutableStateFlow(AddExercisesState())
    val state =
        _state.onStart {
            loadTrainingAndExercises()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = AddExercisesState()
        )

    private fun loadTrainingAndExercises() =
        viewModelScope.launch {
            trainingUseCaseProvider.getOngoingTrainingUseCase().invoke().collect { training ->
                training?.let { trainingNotNull ->
                    _training.update {
                        trainingNotNull
                    }
                    exerciseUseCaseProvider.getLocalExerciseByGroupUseCase()
                        .invoke(groupNames = trainingNotNull.groups)
                        .collect { exercises ->
                            val sortedExercises =
                                withContext(Dispatchers.Default) {
                                    getFilteredList(
                                        availableExercises = getExerciseListWithHeaders(exercises = exercises.sortedBy { it.group }),
                                        selectedExercises = trainingNotNull.exercises
                                    )
                                }
                            _state.update {
                                it.copy(
                                    sortedExercises = sortedExercises,
                                    training = trainingNotNull,
                                    selectedExercises = trainingNotNull.exercises
                                )
                            }
                        }
                }
            }
        }


    fun onEvent(event: AddExercisesEvent) {
        when (event) {
            is AddExercisesEvent.OnSelectExercise -> {
                val list = if (!state.value.selectedExercises.contains(event.exerciseName)) {
                    state.value.selectedExercises.plus(event.exerciseName)
                } else {
                    state.value.selectedExercises.minus(event.exerciseName)
                }
                _state.update {
                    it.copy(selectedExercises = list)
                }
            }

            is AddExercisesEvent.OnAddNewExercises -> {
                state.value.training?.let { oldTraining ->
                    updateTraining(oldTraining)
                    event.onSuccess.invoke()
                }
            }

            AddExercisesEvent.OnDeleteExercise -> {
                viewModelScope.launch {
                    state.value.selectedForDelete?.id?.let {
                        exerciseUseCaseProvider.getDeleteLocalExerciseByIdUseCase().invoke(it)
                        loadTrainingAndExercises()
                    }
                }
                _state.update {
                    it.copy(
                        selectedForDelete = null,
                        isDeleteDialogVisible = false
                    )
                }
            }

            is AddExercisesEvent.OnDisplayDeleteDialog -> {
                _state.update {
                    it.copy(
                        isDeleteDialogVisible = event.isDeleteVisible,
                        selectedForDelete = event.exercise
                    )
                }
            }
        }
    }

    private fun getFilteredList(
        availableExercises: List<ExerciseListItem>,
        selectedExercises: List<String>
    ): List<ExerciseListItem> {
        val totalExerciseItems =
            (availableExercises.count { it is ExerciseListItem.ExerciseItem }
                .minus(selectedExercises.size))

        var exerciseItemCount = 0
        return availableExercises.mapNotNull { item ->
            when (item) {
                is ExerciseListItem.ExerciseItem -> {
                    if (!selectedExercises.contains(item.exercise.name)) {
                        exerciseItemCount++
                        item
                    } else {
                        null
                    }
                }

                is ExerciseListItem.Header -> {
                    if (exerciseItemCount < totalExerciseItems) {
                        item
                    } else {
                        null
                    }
                }
            }
        }
    }

    private fun updateTraining(oldTraining: Training) = viewModelScope.launch(Dispatchers.IO) {
        trainingUseCaseProvider.getInsertTrainingHistoryRecordUseCase()
            .invoke(oldTraining.copy(exercises = state.value.selectedExercises))
        trainingUseCaseProvider.getUpdateTrainingLocalExerciseUseCase().invoke(
            exercises = state.value.selectedExercises,
            id = oldTraining.trainingTemplateId
        )
    }
}

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
