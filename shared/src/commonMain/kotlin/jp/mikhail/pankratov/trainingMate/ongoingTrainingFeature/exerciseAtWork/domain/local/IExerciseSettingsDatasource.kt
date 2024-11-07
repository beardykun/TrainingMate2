package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import kotlinx.coroutines.flow.Flow

interface IExerciseSettingsDatasource {
    suspend fun insertExerciseSettings(exerciseSettings: ExerciseSettings)
    fun getExerciseSettings(trainingTemplateId: Long, exerciseTemplateId: Long): Flow<ExerciseSettings>
    suspend fun updateDefaultIncrementWeight(exerciseTemplateId: Long, weight: Double)
    suspend fun updateIncrementWeightForTraining(trainingTemplateId: Long, exerciseTemplateId: Long, weight: Double)
}