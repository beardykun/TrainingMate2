package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.ui.text.input.TextFieldValue

sealed interface ExerciseSettingsEvent {
    data class OnDefaultIncrementWeightChanged(val newWeight: TextFieldValue) :
        ExerciseSettingsEvent
    data class OnIncrementWeightChanged(val newWeight: TextFieldValue) :
        ExerciseSettingsEvent
    data object OnApplyChanges : ExerciseSettingsEvent
}