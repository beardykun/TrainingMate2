package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

sealed class HistoryInfoEvent {
    data class OnContinueTraining(val onSuccess: () -> Unit) : HistoryInfoEvent()
}