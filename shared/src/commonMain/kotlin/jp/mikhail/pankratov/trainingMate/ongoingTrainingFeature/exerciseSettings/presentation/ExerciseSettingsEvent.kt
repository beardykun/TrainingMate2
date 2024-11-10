package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.ui.text.input.TextFieldValue

sealed interface ExerciseSettingsEvent {
    data class OnDefaultIncrementWeightChanged(val newValue: TextFieldValue) :
        ExerciseSettingsEvent
    data class OnDefaultIntervalSecondsChanged(val newValue: TextFieldValue) :
        ExerciseSettingsEvent
    data class OnIncrementWeightChanged(val newValue: TextFieldValue) :
        ExerciseSettingsEvent
    data class OnIntervalSecondsChanged(val newValue: TextFieldValue) :
        ExerciseSettingsEvent
    data object OnApplyChanges : ExerciseSettingsEvent
}