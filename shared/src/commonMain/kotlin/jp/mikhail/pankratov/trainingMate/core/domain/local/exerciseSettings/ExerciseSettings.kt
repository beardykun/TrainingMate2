package jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings

import androidx.compose.runtime.Immutable

@Immutable
data class ExerciseSettings(
    val id: Long? = null,
    val trainingTemplateId: Long,
    val exerciseTemplateId: Long,
    val defaultSettings: DefaultSettings,
    val exerciseTrainingSettings: ExerciseTrainingSettings,

)

data class DefaultSettings(
    val incrementWeightDefault: Double,
    val isStrengthDefining: Boolean,
    val intervalSecondsDefault: Long,
    val updated: Boolean = false
)

data class ExerciseTrainingSettings(
    val incrementWeightThisTrainingOnly: Double,
    val intervalSeconds: Long,
    val updated: Boolean = false
)
