package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import jp.mikhail.pankratov.trainingMate.core.asResId
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError
import jp.mikhail.pankratov.trainingMate.core.getString
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
                    onEvent(ExerciseSettingsEvent.OnDefaultIncrementWeightChanged(newValue))
                },
                keyboardType = KeyboardType.Decimal,
                isError = state.inputError is InputError.InputErrorFloat,
                errorText = (state.inputError as? InputError.InputErrorFloat)?.asResId()
                    ?.getString() ?: ""
            )
            InputField(
                value = state.intervalSecondsDefault,
                label = "OnDefaultIntervalSecondsChanged",
                placeholder = "OnDefaultIntervalSecondsChanged",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnDefaultIntervalSecondsChanged(newValue))
                },
                keyboardType = KeyboardType.Number,
                isError = state.inputError is InputError.InputErrorInt,
                errorText = (state.inputError as? InputError.InputErrorInt)?.asResId()
                    ?.getString()
            )
            TextLarge(text = "Settings for exercise in This Training")
            InputField(
                value = state.incrementWeightThisTrainingOnly,
                label = "increment_weight",
                placeholder = "increment_weight",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIncrementWeightChanged(newValue))
                },
                isError = state.inputError is InputError.InputErrorFloat,
                errorText = (state.inputError as? InputError.InputErrorFloat)?.asResId()
                    ?.getString() ?: ""
            )
            InputField(
                value = state.intervalSeconds,
                label = "OnIntervalSecondsChanged",
                placeholder = "OnIntervalSecondsChanged",
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIntervalSecondsChanged(newValue))
                },
                keyboardType = KeyboardType.Number,
                isError = state.inputError is InputError.InputErrorInt,
                errorText = (state.inputError as? InputError.InputErrorInt)?.asResId()
                    ?.getString()
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