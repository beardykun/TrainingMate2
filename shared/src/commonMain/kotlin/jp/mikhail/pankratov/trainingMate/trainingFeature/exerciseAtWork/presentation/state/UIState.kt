package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet

data class UIState(
    val isDeleteDialogVisible: Boolean = false,
    val deleteItem: ExerciseSet? = null,
    val isAnimating: Boolean = false,
    val isAutoInputEnabled: Boolean = false
)