package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AddExercisesViewModel(
    exerciseDatasource: IExerciseDatasource,
    groups: String
) : ViewModel() {

    private val _state = MutableStateFlow(AddExercisesState())
    val state =
        combine(_state, exerciseDatasource.getExercisesByGroups(groups)) { state, exercises ->
            if (state.availableExercises != exercises) {
                state.copy(availableExercises = exercises)
            } else state
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            AddExercisesState()
        )

    fun onEvent(event: AddExercisesEvent) {
        when (event) {
            is AddExercisesEvent.OnSelectExercise -> {
                _state.update {
                    it.copy(
                        selectedExercises = state.value.selectedExercises?.plus(event.exercise)
                    )
                }
            }
        }
    }
}