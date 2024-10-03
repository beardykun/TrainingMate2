package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

sealed class SummaryScreenEvent {
    data class OnPageChanged(val pageName: String) : SummaryScreenEvent()
}