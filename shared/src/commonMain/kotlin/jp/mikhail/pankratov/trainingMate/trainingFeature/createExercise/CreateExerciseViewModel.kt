package jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
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
    private val exerciseDatasource: IExerciseDatasource,
    trainingHistoryDataSource: ITrainingHistoryDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CreateExerciseState())
    val state =
        combine(_state, trainingHistoryDataSource.getOngoingTraining()) { state, ongoingTraining ->
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
        viewModelScope.launch {
            if (exerciseDatasource.isExerciseExists(state.value.exerciseName.text)) {
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


    private fun insertNewExercise(onSuccess: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        exerciseDatasource.insertExercise(
            ExerciseLocal(
                name = state.value.exerciseName.text,
                group = state.value.exerciseGroup,
                usesTwoDumbbells = state.value.usesTwoDumbbell,
                image = state.value.exerciseGroup.lowercase()
            )
        )
        withContext(Dispatchers.Main) {
            onSuccess.invoke()
        }
    }
}