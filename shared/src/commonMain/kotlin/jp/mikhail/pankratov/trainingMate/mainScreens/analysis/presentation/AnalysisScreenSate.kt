package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal

data class AnalysisScreenSate(
    val historyTrainings: List<Training>? = null,
    val historyExercises: List<Exercise>? = null,
    val localTrainings: List<TrainingLocal>? = null,
    val localExercises: List<ExerciseLocal>? = null,
    val analysisMode: AnalysisMode = AnalysisMode.WEIGHT,
    val metricsMode: MetricsMode = MetricsMode.GENERAL,
    val graphDisplayed: Boolean = false,
    val metricsData: List<Double>? = null,
    val metricsXAxisData: List<String>? = null
)

enum class AnalysisMode {
    WEIGHT, LENGTH, PROGRESS
}

enum class MetricsMode {
    GENERAL, TRAINING, EXERCISE
}
