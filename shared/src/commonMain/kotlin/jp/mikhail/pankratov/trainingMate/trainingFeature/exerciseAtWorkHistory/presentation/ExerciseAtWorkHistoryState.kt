package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise

data class ExerciseAtWorkHistoryState(val historyList: List<Exercise>? = null)
