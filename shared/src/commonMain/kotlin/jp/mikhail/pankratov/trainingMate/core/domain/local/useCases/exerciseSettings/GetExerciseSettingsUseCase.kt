package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings

import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseSettingsDatasource
import kotlinx.coroutines.flow.Flow

class GetExerciseSettingsUseCase(private val exerciseSettingsDataSource: IExerciseSettingsDatasource) {

    operator fun invoke(
        trainingTemplateId: Long,
        exerciseTemplateId: Long
    ): Flow<ExerciseSettings?> {
        return exerciseSettingsDataSource.getExerciseSettings(
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        )
    }
}