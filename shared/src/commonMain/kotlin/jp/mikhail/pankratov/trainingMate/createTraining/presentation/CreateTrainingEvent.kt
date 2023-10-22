package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import androidx.compose.ui.text.input.TextFieldValue

sealed class CreateTrainingEvent {
    data class OnTrainingNameChanged(val name: TextFieldValue) : CreateTrainingEvent()
    data class OnTrainingGroupsChanged(val group: String) : CreateTrainingEvent()
    data class OnAddNewTraining(val onSuccess: () -> Unit) : CreateTrainingEvent()
}