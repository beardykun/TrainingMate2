package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.data.local

import app.cash.sqldelight.coroutines.asFlow
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseSettingsDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ExerciseSettingsDatasource(db: TrainingDatabase) : IExerciseSettingsDatasource {

    private val queries = db.exerciseSettingsQueries
    override suspend fun insertExerciseSettings(exerciseSettings: ExerciseSettings) {
        queries.insertExerciseSetting(
            id = exerciseSettings.id,
            training_template_id = exerciseSettings.trainingTemplateId,
            exercise_template_id = exerciseSettings.exerciseTemplateId,
            increment_weight_default = exerciseSettings.incrementWeightDefault,
            increment_weight_this_training_only = exerciseSettings.incrementWeightThisTrainingOnly,
            is_strength_defining = if (exerciseSettings.isStrengthDefining) 1 else 0,
            interval_seconds = exerciseSettings.intervalSeconds
        )
    }

    override fun getExerciseSettings(
        trainingTemplateId: Long,
        exerciseTemplateId: Long
    ): Flow<ExerciseSettings> {
        return queries.getExerciseSettings(
            training_template_id = trainingTemplateId,
            exercise_template_id = exerciseTemplateId
        ).asFlow().map {
            it.executeAsOne().toExerciseSettings()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateDefaultIncrementWeight(
        exerciseTemplateId: Long,
        weight: Double
    ) {
        queries.updateDefaultIncrementWeight(
            increment_weight_default = weight,
            exercise_template_id = exerciseTemplateId
        )
    }

    override suspend fun updateIncrementWeightForTraining(
        trainingTemplateId: Long,
        exerciseTemplateId: Long,
        weight: Double
    ) {
        queries.updateIncrementWeightForTraining(
            increment_weight_this_training_only = weight,
            training_template_id = trainingTemplateId,
            exercise_template_id = exerciseTemplateId
        )
    }
}