package jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise

import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class CreateExerciseState(
    val exerciseName: TextFieldValue = TextFieldValue(""),
    val exerciseGroup: String = "",
    val usesTwoDumbbell: Boolean = false,
    val ongoingTraining: Training? = null,
    val invalidNameInput: Boolean = false,
    val invalidGroupInput: Boolean = false
)