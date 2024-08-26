package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import androidx.compose.ui.text.input.TextFieldValue

data class CreateTrainingState(
    val trainingName: TextFieldValue = TextFieldValue(""),
    val trainingDescription: String = "",
    val selectedGroups: List<String> = emptyList(),
    val invalidNameInput: Boolean = false
)