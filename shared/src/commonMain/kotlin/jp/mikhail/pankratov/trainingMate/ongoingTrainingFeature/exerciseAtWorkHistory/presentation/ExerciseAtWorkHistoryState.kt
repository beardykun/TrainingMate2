package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise

@Immutable
data class ExerciseAtWorkHistoryState(
    val historyExercises: List<Exercise>? = null,
    val historyExercisesToDisplay: List<Exercise>? = null
)
