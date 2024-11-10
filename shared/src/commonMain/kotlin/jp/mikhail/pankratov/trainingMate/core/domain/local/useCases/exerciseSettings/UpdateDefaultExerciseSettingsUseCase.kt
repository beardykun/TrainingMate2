package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings

import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseSettingsDatasource

class UpdateDefaultExerciseSettingsUseCase(private val exerciseSettingsDataSource: IExerciseSettingsDatasource) {
    suspend operator fun invoke(exerciseSettings: ExerciseSettings) {
        exerciseSettingsDataSource.updateDefaultSettings(
            exerciseTemplateId = exerciseSettings.exerciseTemplateId,
            weight = exerciseSettings.defaultSettings.incrementWeightDefault,
            intervalSeconds = exerciseSettings.defaultSettings.intervalSecondsDefault,
        )
    }
}