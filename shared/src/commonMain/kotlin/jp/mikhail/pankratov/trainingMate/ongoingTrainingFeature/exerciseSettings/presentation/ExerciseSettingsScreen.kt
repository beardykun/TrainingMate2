package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import jp.mikhail.pankratov.trainingMate.core.asResId
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonButton
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.apply_changes
import maxrep.shared.generated.resources.global_exercise_settings
import maxrep.shared.generated.resources.global_exercise_settings_hint
import maxrep.shared.generated.resources.increment_weight_label
import maxrep.shared.generated.resources.increment_weight_placeholder
import maxrep.shared.generated.resources.interval_seconds_label
import maxrep.shared.generated.resources.interval_seconds_placeholder
import maxrep.shared.generated.resources.this_training_exercise_settings
import maxrep.shared.generated.resources.this_training_exercise_settings_hint
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ExerciseSettingsScreen(
    state: ExerciseSettingsState, onEvent: (ExerciseSettingsEvent) -> Unit, navigator: Navigator
) {
    state.exerciseSettings?.let {
        Column(modifier = Modifier.padding(all = Dimens.Padding16)) {
            TextLarge(text = Res.string.global_exercise_settings.getString())
            TextSmall(text = Res.string.global_exercise_settings_hint.getString())
            InputField(
                value = state.incrementWeightDefault,
                label = Res.string.increment_weight_label.getString(),
                placeholder = Res.string.increment_weight_placeholder.getString(),
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnDefaultIncrementWeightChanged(newValue))
                },
                keyboardType = KeyboardType.Decimal,
                isError = state.inputErrorDefaultIncrementWeight is InputError.InputErrorFloat,
                errorText = (state.inputErrorDefaultIncrementWeight as? InputError.InputErrorFloat)?.asResId()
                    ?.getString() ?: ""
            )
            InputField(
                value = state.intervalSecondsDefault,
                label = Res.string.interval_seconds_label.getString(),
                placeholder = Res.string.interval_seconds_placeholder.getString(),
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnDefaultIntervalSecondsChanged(newValue))
                },
                keyboardType = KeyboardType.Number,
                isError = state.inputErrorDefaultIntervalSeconds is InputError.InputErrorInt,
                errorText = (state.inputErrorDefaultIntervalSeconds as? InputError.InputErrorInt)?.asResId()
                    ?.getString()
            )
            TextLarge(text = Res.string.this_training_exercise_settings.getString())
            TextSmall(text = Res.string.this_training_exercise_settings_hint.getString())
            InputField(
                value = state.incrementWeightThisTrainingOnly,
                label = Res.string.increment_weight_label.getString(),
                placeholder = Res.string.increment_weight_placeholder.getString(),
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIncrementWeightChanged(newValue))
                },
                isError = state.inputErrorIncrementWeight is InputError.InputErrorFloat,
                errorText = (state.inputErrorIncrementWeight as? InputError.InputErrorFloat)?.asResId()
                    ?.getString() ?: ""
            )
            InputField(
                value = state.intervalSeconds,
                label = Res.string.interval_seconds_label.getString(),
                placeholder = Res.string.interval_seconds_placeholder.getString(),
                onValueChanged = { newValue ->
                    onEvent(ExerciseSettingsEvent.OnIntervalSecondsChanged(newValue))
                },
                keyboardType = KeyboardType.Number,
                isError = state.inputErrorIntervalSeconds is InputError.InputErrorInt,
                errorText = (state.inputErrorIntervalSeconds as? InputError.InputErrorInt)?.asResId()
                    ?.getString()
            )
            Spacer(modifier = Modifier.weight(1f))
            CommonButton(
                text = Res.string.apply_changes.getString(),
                onClick = {
                    onEvent(ExerciseSettingsEvent.OnApplyChanges {
                        navigator.goBack()
                    })
                }
            )
        }
    }
}