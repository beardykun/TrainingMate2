package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HistoryScreenViewModel(trainingHistoryDataSource: ITrainingHistoryDataSource) : ViewModel() {

    private val _state = MutableStateFlow(HistoryScreenState())
    val state = combine(
        trainingHistoryDataSource.getLatestHistoryTrainings(),
        _state
    ) { historyList, state ->
        if (state.historyList != historyList) {
            state.copy(historyList = historyList)
        } else state
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = HistoryScreenState()
    )
}