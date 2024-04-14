package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.CountExerciseInHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.DeleteLocalExerciseByIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetAllLocalExercisesUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetExerciseFromHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetExercisesForTrainingHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLatsSameHistoryExerciseUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExerciseByGroupUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExerciseByTemplateIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExercisesByNamesUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.InsertExerciseHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.InsertLocalExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain.IsLocalExerciseExistsUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateBestLiftedWeightUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateHistoryExerciseDataUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.domain.useCases.GetHistoryExercisesWithNameUseCase
import org.koin.dsl.module

fun exerciseUseCaseModule() = module {
    single { ExerciseUseCaseProvider(getKoin()) }
    //Local
    single { UpdateBestLiftedWeightUseCase(exerciseDataSource = get()) }
    single { GetLocalExerciseByTemplateIdUseCase(exerciseDatasource = get()) }
    single { IsLocalExerciseExistsUseCase(exerciseDatasource = get()) }
    single { GetLocalExerciseByGroupUseCase(exerciseDatasource = get()) }
    single { DeleteLocalExerciseByIdUseCase(exerciseDatasource = get()) }
    single { InsertLocalExerciseUseCase(exerciseDatasource = get()) }
    single { GetLocalExercisesByNamesUseCase(exerciseDatasource = get()) }
    single { GetAllLocalExercisesUseCase(exerciseDatasource = get()) }
    //History
    single { GetExerciseFromHistoryUseCase(exerciseHistoryDatasource = get()) }
    single { GetLatsSameHistoryExerciseUseCase(exerciseHistoryDatasource = get()) }
    single { UpdateHistoryExerciseDataUseCase(exerciseHistoryDatasource = get()) }
    single { GetHistoryExercisesWithNameUseCase(exerciseHistoryDatasource = get()) }
    single { InsertExerciseHistoryUseCase(exerciseHistoryDatasource = get()) }
    single { CountExerciseInHistoryUseCase(exerciseHistoryDatasource = get()) }
    single { GetExercisesForTrainingHistoryUseCase(exerciseHistoryDatasource = get()) }
}