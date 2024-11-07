package jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings

data class ExerciseSettings(
    val id: Long? = null,
    val trainingTemplateId: Long,
    val exerciseTemplateId: Long,
    val incrementWeightDefault: Double,
    val incrementWeightThisTrainingOnly: Double,
    val isStrengthDefining: Boolean,
    val intervalSeconds: Long
)
