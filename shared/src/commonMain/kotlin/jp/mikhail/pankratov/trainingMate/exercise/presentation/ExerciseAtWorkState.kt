package jp.mikhail.pankratov.trainingMate.exercise.presentation

import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class ExerciseAtWorkState(
    val training: Training? = null,
    val exercise: Exercise? = null,
    val timer: Int = 60,
    val isExpanded: Boolean = false,
    val weight: TextFieldValue = TextFieldValue(""),
    val reps: TextFieldValue = TextFieldValue(""),
    val errorReps: String? = null,
    val errorWeight: String? = null,
    val isDeleteDialogVisible: Boolean = false,
    val deleteItem: String? = null
)