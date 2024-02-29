package jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise

import androidx.compose.ui.text.input.TextFieldValue

sealed class CreateExerciseEvent {
    data class OnExerciseNameChanged(val newName: TextFieldValue) : CreateExerciseEvent()
    data class OnExerciseGroupChanged(val newGroup: String) : CreateExerciseEvent()
    data object OnExerciseUsesTwoDumbbells : CreateExerciseEvent()
    data class OnExerciseCreate(val onSuccess: () -> Unit) : CreateExerciseEvent()
}