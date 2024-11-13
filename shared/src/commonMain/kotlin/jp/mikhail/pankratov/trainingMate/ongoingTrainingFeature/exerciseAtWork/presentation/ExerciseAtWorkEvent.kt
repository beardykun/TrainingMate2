package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation

import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases.AutoInputMode

sealed interface ExerciseAtWorkEvent {
    data object OnTimerStart : ExerciseAtWorkEvent
    data object OnTimerStop : ExerciseAtWorkEvent
    data object OnAddNewSet : ExerciseAtWorkEvent
    data object OnDropdownOpen : ExerciseAtWorkEvent
    data object OnDropdownClosed : ExerciseAtWorkEvent
    data object OnDropdownItemSelected : ExerciseAtWorkEvent
    data class OnMinutesUpdated(val newMinutes: Int) : ExerciseAtWorkEvent
    data class OnSecondsUpdated(val newSeconds: Int) : ExerciseAtWorkEvent
    data class OnWeightChanged(val newWeight: TextFieldValue) : ExerciseAtWorkEvent
    data class OnRepsChanged(val newReps: TextFieldValue) : ExerciseAtWorkEvent
    data object OnSetDelete : ExerciseAtWorkEvent
    data object OnAnimationSeen : ExerciseAtWorkEvent
    data class OnDisplayDeleteDialog(val display: Boolean = false, val item: ExerciseSet? = null) :
        ExerciseAtWorkEvent

    data class OnSetDifficultySelected(val difficulty: SetDifficulty) : ExerciseAtWorkEvent
    data class OnAutoInputChanged(val autoInputMode: AutoInputMode) : ExerciseAtWorkEvent
    data object OnRefreshAutoInputValues: ExerciseAtWorkEvent
}
