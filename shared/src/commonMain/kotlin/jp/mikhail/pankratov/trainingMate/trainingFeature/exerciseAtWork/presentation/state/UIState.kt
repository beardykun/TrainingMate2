package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.AutoInputMode

@Immutable
data class UIState(
    val isDeleteDialogVisible: Boolean = false,
    val deleteItem: ExerciseSet? = null,
    val isAnimating: Boolean = false,
    val autoInputSelected: AutoInputMode = AutoInputMode.NONE
)
