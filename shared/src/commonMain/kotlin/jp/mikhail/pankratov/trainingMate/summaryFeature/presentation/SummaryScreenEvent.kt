package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

sealed class SummaryScreenEvent {
    data class OnTabChanged(val pageName: String) : SummaryScreenEvent()
}