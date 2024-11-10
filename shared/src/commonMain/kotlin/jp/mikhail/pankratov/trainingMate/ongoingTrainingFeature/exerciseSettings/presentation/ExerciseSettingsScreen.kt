package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
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
                value = TextFieldValue(exerciseSettings.defaultSettings.incrementWeightDefault.toString()),
                label = "increment_default_weight",
                placeholder = "increment_default_weight",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnDefaultIncrementWeightChanged(newValue))
                }
            )
            InputField(
                value = TextFieldValue(exerciseSettings.defaultSettings.intervalSecondsDefault.toString()),
                label = "OnDefaultIntervalSecondsChanged",
                placeholder = "OnDefaultIntervalSecondsChanged",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnDefaultIntervalSecondsChanged(newValue))
                }
            )
            TextLarge(text = "Settings for exercise in This Training")
            InputField(
                value = TextFieldValue(exerciseSettings.exerciseTrainingSettings.incrementWeightThisTrainingOnly.toString()),
                label = "increment_weight",
                placeholder = "increment_weight",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIncrementWeightChanged(newValue))
                }
            )
            InputField(
                value = TextFieldValue(exerciseSettings.exerciseTrainingSettings.intervalSeconds.toString()),
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