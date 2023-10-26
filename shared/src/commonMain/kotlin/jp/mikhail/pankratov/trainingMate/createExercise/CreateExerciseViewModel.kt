package jp.mikhail.pankratov.trainingMate.createExercise

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

            CreateExerciseEvent.OnExerciseCreate -> {
                //TODO validate inputs
                insertNewExercise()
            }
        }
    }

    private fun insertNewExercise() = viewModelScope.launch(Dispatchers.IO) {
        exerciseDatasource.insertExercise(
            ExerciseLocal(
                name = state.value.exerciseName,
                group = state.value.exerciseGroup,
                usesTwoDumbbells = state.value.usesTwoDumbbell,
                image = state.value.exerciseGroup.lowercase()
            )
        )
    }
}