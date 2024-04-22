package jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class AddExercisesViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider
) : moe.tlaster.precompose.viewmodel.ViewModel() {

    private val _training = MutableStateFlow<Training?>(null)
    private val _availableExercises = MutableStateFlow<List<ExerciseLocal>>(emptyList())
    private val _selectedExercises = MutableStateFlow<List<String>>(emptyList())

    private val _state = MutableStateFlow(AddExercisesState())
    val state = combine(
        _state,
        _training,
        _availableExercises,
        _selectedExercises
    ) { state, training, availableExercises, selectedExercises ->
        state.copy(
            availableExerciseLocals = getExerciseListWithHeaders(availableExercises.sortedBy { it.group }),
            selectedExercises = selectedExercises,
            training = training
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = AddExercisesState()
    )

    init {
        loadTrainingAndExercises()
    }

    private fun loadTrainingAndExercises() =
        viewModelScope.launch {
            val training = trainingUseCaseProvider.getOngoingTrainingUseCase().invoke().first()
            training?.let { trainingNotNull ->
                _training.update {
                    trainingNotNull
                }
                val exercises =
                    exerciseUseCaseProvider.getLocalExerciseByGroupUseCase()
                        .invoke(groupNames = trainingNotNull.groups)
                        .first()
                _selectedExercises.update { trainingNotNull.exercises }
                _availableExercises.update { exercises }
            }
        }


    fun onEvent(event: AddExercisesEvent) {
        when (event) {
            is AddExercisesEvent.OnSelectExercise -> {
                _selectedExercises.update {
                    val list = if (!state.value.selectedExercises.contains(event.exercise)) {
                        state.value.selectedExercises.plus(event.exercise)
                    } else {
                        state.value.selectedExercises.minus(event.exercise)
                    }
                    list
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

    private fun updateTraining(oldTraining: Training) = viewModelScope.launch(Dispatchers.IO) {
        trainingUseCaseProvider.getInsertTrainingHistoryRecordUseCase()
            .invoke(oldTraining.copy(exercises = _selectedExercises.value))
        trainingUseCaseProvider.getUpdateTrainingLocalExerciseUseCase().invoke(
            exercises = _selectedExercises.value,
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
