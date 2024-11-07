package jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings

data class ExerciseSettingsLocal(
    val id: Int? = null,
    val trainingTemplateId: Int,
    val exerciseTemplateId: Int,
    val incrementWeightDefault: Double,
    val incrementWeightThisTrainingOnly: Double,
    val isStrengthDefining: Boolean
)
