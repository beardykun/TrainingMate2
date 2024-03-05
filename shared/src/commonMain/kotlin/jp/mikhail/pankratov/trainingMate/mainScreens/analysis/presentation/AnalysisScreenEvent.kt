package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

sealed class AnalysisScreenEvent {
    data class OnMetricsModeChanged(val metricsMode: MetricsMode) : AnalysisScreenEvent()
    data class OnAnalysisModeChanged(val analysisMode: String) : AnalysisScreenEvent()
    data object OnGeneralSelected : AnalysisScreenEvent()
    data object OnDropdownOpen : AnalysisScreenEvent()
    data object OnDropdownClosed : AnalysisScreenEvent()
    data class OnExerciseNameSelected(val exerciseName: String) : AnalysisScreenEvent()
    data class OnTrainingIdSelected(val trainingId: Long, val name: String) : AnalysisScreenEvent()

}
