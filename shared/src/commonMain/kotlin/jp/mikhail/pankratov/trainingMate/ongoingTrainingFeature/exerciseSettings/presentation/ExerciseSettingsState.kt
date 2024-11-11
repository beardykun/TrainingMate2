package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError

@Immutable
data class ExerciseSettingsState(
    val exerciseSettings: ExerciseSettings? = null,
    val incrementWeightDefault: TextFieldValue = TextFieldValue(""),
    val intervalSecondsDefault: TextFieldValue = TextFieldValue(""),
    val incrementWeightThisTrainingOnly: TextFieldValue = TextFieldValue(""),
    val intervalSeconds: TextFieldValue = TextFieldValue(""),
    val inputError: InputError? = null
)