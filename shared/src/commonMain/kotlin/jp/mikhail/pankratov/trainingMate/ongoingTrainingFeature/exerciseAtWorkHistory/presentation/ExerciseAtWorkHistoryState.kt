package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise

@Immutable
data class ExerciseAtWorkHistoryState(val historyList: List<Exercise>? = null)
