package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings

@Immutable
data class ExerciseSettingsState(
    val exerciseSettings: ExerciseSettings? = null,
    val incrementWeightDefault: TextFieldValue = TextFieldValue(""),
    val defaultIntervalSeconds: TextFieldValue = TextFieldValue(""),
    val incrementWeight: TextFieldValue = TextFieldValue(""),
    val intervalSeconds: TextFieldValue = TextFieldValue("")
)