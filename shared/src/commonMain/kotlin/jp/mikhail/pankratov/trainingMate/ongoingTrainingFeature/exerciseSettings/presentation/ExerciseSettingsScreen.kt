package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField

@Composable
fun ExerciseSettingsScreen(state: ExerciseSettingsState, onEvent: (ExerciseSettingsEvent) -> Unit) {
    state.exerciseSettings?.let { exerciseSettings ->
        Column {
            InputField(
                value = TextFieldValue(exerciseSettings.incrementWeightDefault.toString()),
                label = "Res.string.increment_default_weight",
                placeholder = "Res.string.increment_default_weight",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnDefaultIncrementWeightChanged(newValue))
                }
            )
            InputField(
                value = TextFieldValue(exerciseSettings.incrementWeightThisTrainingOnly.toString()),
                label = "Res.string.increment_weight",
                placeholder = "Res.string.increment_weight",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIncrementWeightChanged(newValue))
                }
            )
        }
    }
}