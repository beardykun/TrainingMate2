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
            increment_weight_default = exerciseSettings.defaultSettings.incrementWeightDefault,
            increment_weight_this_training_only = exerciseSettings.exerciseTrainingSettings.incrementWeightThisTrainingOnly,
            interval_seconds = exerciseSettings.exerciseTrainingSettings.intervalSeconds,
            interval_seconds_default = exerciseSettings.defaultSettings.intervalSecondsDefault
        )
    }

    override fun getExerciseSettings(
        trainingTemplateId: Long,
        exerciseTemplateId: Long
    ): Flow<ExerciseSettings?> {
        return queries.getExerciseSettings(
            training_template_id = trainingTemplateId,
            exercise_template_id = exerciseTemplateId
        ).asFlow().map {
            it.executeAsOneOrNull()?.toExerciseSettings()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateDefaultSettings(
        exerciseTemplateId: Long,
        weight: Double,
        intervalSeconds: Long
    ) {
        queries.updateDefaultSettings(
            increment_weight_default = weight,
            interval_seconds_default = intervalSeconds,
            exercise_template_id = exerciseTemplateId
        )
    }

    override suspend fun updateTrainingExerciseSettings(
        trainingTemplateId: Long,
        exerciseTemplateId: Long,
        weight: Double?,
        intervalSeconds: Long?
    ) {
        queries.updateTrainingExerciseSettings(
            increment_weight_this_training_only = weight,
            interval_seconds = intervalSeconds,
            training_template_id = trainingTemplateId,
            exercise_template_id = exerciseTemplateId
        )
    }
}