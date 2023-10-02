package jp.mikhail.pankratov.trainingMate.exercise.presentation

import androidx.compose.ui.text.input.TextFieldValue

sealed class ExerciseAtWorkEvent {
    data object OnTimerStart : ExerciseAtWorkEvent()
    data object OnAddNewSet : ExerciseAtWorkEvent()
    data object OnDropdownOpen : ExerciseAtWorkEvent()
    data object OnDropdownClosed : ExerciseAtWorkEvent()
    data class OnDropdownItemSelected(val item: String) : ExerciseAtWorkEvent()
    data class OnWeightChanged(val newWeight: TextFieldValue) : ExerciseAtWorkEvent()
    data class OnRepsChanged(val newReps: TextFieldValue) : ExerciseAtWorkEvent()
    data object OnSetDelete : ExerciseAtWorkEvent()
    data class OnDisplayDeleteDialog(val display: Boolean = false, val item: String? = null) : ExerciseAtWorkEvent()
}
