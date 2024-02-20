package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryScreenViewModel(private val trainingHistoryDataSource: ITrainingHistoryDataSource) :
    ViewModel() {

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

    fun onEvent(event: HistoryScreenEvent) {
        when (event) {
            is HistoryScreenEvent.OnDeleteClick -> {
                _state.update {
                    it.copy(
                        trainingId = event.trainingId,
                        showDeleteDialog = true
                    )
                }
            }

            HistoryScreenEvent.OnDeleteConfirmClick -> {
                _state.update {
                    it.copy(
                        trainingId = null,
                        showDeleteDialog = false
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    state.value.trainingId?.let { trainingHistoryDataSource.deleteTrainingRecord(trainingId = it) }
                }
            }

            HistoryScreenEvent.OnDeleteDenyClick -> {
                _state.update {
                    it.copy(
                        trainingId = null,
                        showDeleteDialog = false
                    )
                }
            }
        }
    }
}