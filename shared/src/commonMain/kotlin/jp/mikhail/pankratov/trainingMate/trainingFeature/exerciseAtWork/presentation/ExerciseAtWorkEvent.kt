package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet

sealed class ExerciseAtWorkEvent {
    data object OnTimerStart : ExerciseAtWorkEvent()
    data object OnAddNewSet : ExerciseAtWorkEvent()
    data object OnDropdownOpen : ExerciseAtWorkEvent()
    data object OnDropdownClosed : ExerciseAtWorkEvent()
    data class OnDropdownItemSelected(val item: String) : ExerciseAtWorkEvent()
    data class OnWeightChanged(val newWeight: TextFieldValue) : ExerciseAtWorkEvent()
    data class OnRepsChanged(val newReps: TextFieldValue) : ExerciseAtWorkEvent()
    data object OnSetDelete : ExerciseAtWorkEvent()
    data object OnAnimationSeen : ExerciseAtWorkEvent()
    data class OnDisplayDeleteDialog(val display: Boolean = false, val item: ExerciseSet? = null) :
        ExerciseAtWorkEvent()
}
