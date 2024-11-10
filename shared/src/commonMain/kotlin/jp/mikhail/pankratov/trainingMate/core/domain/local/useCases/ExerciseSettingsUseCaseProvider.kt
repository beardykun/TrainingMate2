package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.GetExerciseSettingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.InsertExerciseSettingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.UpdateDefaultExerciseSettingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.UpdateExerciseSettingsUseCase
import org.koin.core.Koin

class ExerciseSettingsUseCaseProvider(val koin: Koin) {
    inline fun <reified T : Any> get(): T = koin.get()

    fun getExerciseSettingsUseCase(): GetExerciseSettingsUseCase = get()
    fun insertExerciseSettingsUseCase(): InsertExerciseSettingsUseCase = get()
    fun updateDefaultExerciseSettingsUseCase(): UpdateDefaultExerciseSettingsUseCase = get()
    fun updateExerciseSettingsUseCase(): UpdateExerciseSettingsUseCase = get()
}