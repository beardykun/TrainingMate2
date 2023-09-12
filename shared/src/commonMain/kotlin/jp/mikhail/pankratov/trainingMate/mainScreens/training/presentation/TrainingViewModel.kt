package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class TrainingViewModel(private val trainingDataSource: ITrainingDataSource) : ViewModel() {

    private val _state = MutableStateFlow(TrainingScreenState())
    val state = combine(_state, trainingDataSource.getTrainings()) { state, trainings ->
        if (state.availableTrainings != trainings) {
            state.copy(availableTrainings = trainings)
        } else state
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = TrainingScreenState()
    )

    fun onEvent(event: TrainingScreenEvent) {

    }
}