package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ThisTrainingViewModel(
    trainingDataSource: ITrainingDataSource,
    trainingId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(ThisTrainingState())
    val state = combine(
        _state,
        trainingDataSource.getTrainingById(trainingId)
    ) { state, training ->
        if (state.training != training) {
            state.copy(training = training)
        } else state
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        ThisTrainingState()
    )

    fun onEvent(event: ThisTrainingEvent) {

    }
}