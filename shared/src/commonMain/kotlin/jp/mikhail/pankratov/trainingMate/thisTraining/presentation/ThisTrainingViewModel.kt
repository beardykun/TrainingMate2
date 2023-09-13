package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ThisTrainingViewModel(private val exerciseDatasource: IExerciseDatasource) : ViewModel() {

    private val _state = MutableStateFlow(ThisTrainingState())
    val state = combine(_state, exerciseDatasource.getAllExercises()) { state, exercises ->
        if (state.exercises != exercises) {
            state.copy(exercises = exercises)
        } else state
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        ThisTrainingState()
    )

    fun onEvent(event: ThisTrainingEvent) {

    }
}