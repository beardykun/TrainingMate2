package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.createExercise.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateExerciseViewModel(
    trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider
) : ViewModel() {

    private val _state = MutableStateFlow(CreateExerciseState())
    val state =
        combine(
            _state,
            trainingUseCaseProvider.getOngoingTrainingUseCase().invoke()
        ) { state, ongoingTraining ->
            state.copy(ongoingTraining = ongoingTraining)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = CreateExerciseState()
        )

    fun onEvent(event: CreateExerciseEvent) {
        when (event) {
            is CreateExerciseEvent.OnExerciseGroupChanged -> {
                _state.update {
                    it.copy(
                        exerciseGroup = event.newGroup
                    )
                }
            }

            is CreateExerciseEvent.OnExerciseNameChanged -> {
                _state.update {
                    it.copy(
                        exerciseName = event.newName
                    )
                }
            }

            CreateExerciseEvent.OnExerciseUsesTwoDumbbells -> {
                _state.update {
                    it.copy(usesTwoDumbbell = state.value.usesTwoDumbbell.not())
                }
            }

            is CreateExerciseEvent.OnExerciseCreate -> {
                if (validNameAndGroupInput().not()) return

                validateItNotInDbAndInsert(event)
            }
        }
    }

    private fun validateItNotInDbAndInsert(event: CreateExerciseEvent.OnExerciseCreate) {
        viewModelScope.launch(Dispatchers.IO) {
            if (exerciseUseCaseProvider.getIsLocalExerciseExistsUseCase()
                    .invoke(state.value.exerciseName.text)
            ) {
                _state.update {
                    it.copy(invalidNameInput = true)
                }
            } else {
                _state.update {
                    it.copy(invalidNameInput = false)
                }
                insertNewExercise(event.onSuccess)
            }
        }
    }

    private fun validNameAndGroupInput(): Boolean {
        val trainingName = state.value.exerciseName.text
        return if (trainingName.isBlank() || trainingName.length < 2) {
            _state.update {
                it.copy(invalidNameInput = true)
            }
            false
        } else if (state.value.exerciseGroup.isBlank()) {
            _state.update {
                it.copy(invalidGroupInput = true)
            }
            false
        } else true
    }


    private suspend fun insertNewExercise(onSuccess: () -> Unit) {
        val stateValue = state.value
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = stateValue.exerciseName.text,
                group = stateValue.exerciseGroup,
                image = stateValue.exerciseGroup.lowercase(),
                usesTwoDumbbells = stateValue.usesTwoDumbbell
            )
        )
        withContext(Dispatchers.Main) {
            onSuccess.invoke()
        }
    }
}