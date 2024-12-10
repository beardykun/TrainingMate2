package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

sealed class SummaryScreenEvent {
    data class OnTabChanged(val tab: SummaryTabs) : SummaryScreenEvent()
}