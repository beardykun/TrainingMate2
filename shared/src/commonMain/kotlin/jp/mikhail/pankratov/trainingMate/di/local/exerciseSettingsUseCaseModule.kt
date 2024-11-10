package jp.mikhail.pankratov.trainingMate.di.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseSettingsUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.GetExerciseSettingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.InsertExerciseSettingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.UpdateDefaultExerciseSettingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exerciseSettings.UpdateExerciseSettingsUseCase
import org.koin.dsl.module

fun exerciseSettingsUseCaseModule() = module {
    single { ExerciseSettingsUseCaseProvider(getKoin()) }

    single { GetExerciseSettingsUseCase(exerciseSettingsDataSource = get()) }
    single { InsertExerciseSettingsUseCase(exerciseSettingsDataSource = get()) }
    single { UpdateDefaultExerciseSettingsUseCase(exerciseSettingsDataSource = get()) }
    single { UpdateExerciseSettingsUseCase(exerciseSettingsDataSource = get()) }
}