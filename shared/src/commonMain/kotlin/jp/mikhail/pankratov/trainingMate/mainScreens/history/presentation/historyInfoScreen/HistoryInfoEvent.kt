package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

sealed class HistoryInfoEvent {
    data class OnContinueTraining(val onSuccess: () -> Unit, val onError: () -> Unit) :
        HistoryInfoEvent()

    data object OnError : HistoryInfoEvent()
    data object OnFinishDeny : HistoryInfoEvent()
    data class OnFinishOngoingAndContinue(val onSuccess: () -> Unit) : HistoryInfoEvent()
}