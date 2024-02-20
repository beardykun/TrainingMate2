package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

sealed class HistoryScreenEvent {
    data class OnDeleteClick(val trainingId: Long) : HistoryScreenEvent()
    data object OnDeleteConfirmClick: HistoryScreenEvent()
    data object OnDeleteDenyClick: HistoryScreenEvent()
}