package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonButton
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ExerciseSettingsScreen(
    state: ExerciseSettingsState, onEvent: (ExerciseSettingsEvent) -> Unit, navigator: Navigator
) {
    state.exerciseSettings?.let { exerciseSettings ->
        Column {
            TextLarge(text = "Default settings for this exercise")
            InputField(
                value = state.incrementWeightDefault,
                label = "increment_default_weight",
                placeholder = "increment_default_weight",
                onValueChanged = { newValue ->
                    println("TAGGER $newValue")
                    onEvent(ExerciseSettingsEvent.OnDefaultIncrementWeightChanged(newValue))
                },
                keyboardType = KeyboardType.Decimal
            )
            InputField(
                value = state.intervalSecondsDefault,
                label = "OnDefaultIntervalSecondsChanged",
                placeholder = "OnDefaultIntervalSecondsChanged",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnDefaultIntervalSecondsChanged(newValue))
                },
                keyboardType = KeyboardType.Number
            )
            TextLarge(text = "Settings for exercise in This Training")
            InputField(
                value = state.incrementWeightThisTrainingOnly,
                label = "increment_weight",
                placeholder = "increment_weight",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIncrementWeightChanged(newValue))
                }
            )
            InputField(
                value = state.intervalSeconds,
                label = "OnIntervalSecondsChanged",
                placeholder = "OnIntervalSecondsChanged",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIntervalSecondsChanged(newValue))
                }
            )
            CommonButton(
                text = "Apply Changes",
                onClick = {
                    onEvent(ExerciseSettingsEvent.OnApplyChanges {
                        navigator.goBack()
                    })
                }
            )
        }
    }
}