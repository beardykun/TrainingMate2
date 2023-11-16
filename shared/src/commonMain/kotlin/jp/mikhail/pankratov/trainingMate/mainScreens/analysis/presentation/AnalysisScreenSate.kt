package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class AnalysisScreenSate(
    val historyTrainings: Training? = null,
    val analysisMode: AnalysisMode = AnalysisMode.WEIGHT,
    val metricsMode: MetricsMode = MetricsMode.GENERAL
)

enum class AnalysisMode {
    WEIGHT, LENGTH, PROGRESS
}

enum class MetricsMode {
    GENERAL, MUSCLE_GROUP, TRAINING, EXERCISE
}
