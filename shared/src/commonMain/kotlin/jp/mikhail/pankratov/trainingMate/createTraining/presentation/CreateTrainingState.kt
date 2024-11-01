package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue

@Immutable
data class CreateTrainingState(
    val trainingName: TextFieldValue = TextFieldValue(""),
    val trainingDescription: TextFieldValue = TextFieldValue(""),
    val selectedGroups: List<String> = emptyList(),
    val invalidNameInput: Boolean = false,
    val invalidDescriptionInput: Boolean = false
)