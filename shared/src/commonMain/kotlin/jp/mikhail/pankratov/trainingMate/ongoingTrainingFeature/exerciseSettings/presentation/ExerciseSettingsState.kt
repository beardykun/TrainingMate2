package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings

@Immutable
data class ExerciseSettingsState(val exerciseSettings: ExerciseSettings? = null)