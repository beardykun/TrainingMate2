package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonButton
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ExerciseSettingsScreen(
    state: ExerciseSettingsState, onEvent: (ExerciseSettingsEvent) -> Unit, navigator: Navigator
) {
    Column {
        TextLarge(text = "Default settings for this exercise")
        InputField(
            value = state.incrementWeightDefault,
            label = "increment_default_weight",
            placeholder = "increment_default_weight",
            onValueChanged = { newValue ->
                onEvent(ExerciseSettingsEvent.OnDefaultIncrementWeightChanged(newValue))
            }
        )
        InputField(
            value = state.defaultIntervalSeconds,
            label = "OnDefaultIntervalSecondsChanged",
            placeholder = "OnDefaultIntervalSecondsChanged",
            onValueChanged = { newValue ->
                onEvent(ExerciseSettingsEvent.OnDefaultIntervalSecondsChanged(newValue))
            }
        )
        TextLarge(text = "Settings for exercise in This Training")
        InputField(
            value = state.incrementWeight,
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