package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

sealed class AnalysisScreenEvent {
    data class OnMetricsModeChanged(val metricsMode: MetricsMode): AnalysisScreenEvent()
    data object OnGeneralSelected : AnalysisScreenEvent()
    data class OnExerciseNameSelected(val exerciseName: String) : AnalysisScreenEvent()
    data class OnTrainingIdSelected(val trainingId: Long) : AnalysisScreenEvent()
    data class OnTrainingSelected(val trainingName: String) : AnalysisScreenEvent()
    data class OnGroupSelected(val group: String) : AnalysisScreenEvent()
}
