package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import kotlinx.coroutines.flow.Flow

interface IExerciseSettingsDatasource {
    suspend fun insertExerciseSettings(exerciseSettings: ExerciseSettings)
    fun getExerciseSettings(
        trainingTemplateId: Long,
        exerciseTemplateId: Long
    ): Flow<ExerciseSettings?>

    suspend fun updateDefaultSettings(
        exerciseTemplateId: Long,
        weight: Double,
        intervalSeconds: Long,
        isStrengthDefining: Boolean
    )

    suspend fun updateTrainingExerciseSettings(
        trainingTemplateId: Long,
        exerciseTemplateId: Long,
        weight: Double,
        intervalSeconds: Long
    )
}