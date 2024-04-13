package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.GetExerciseByTemplateIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.GetExerciseFromHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.GetLatsSameExerciseUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.GetOngoingTrainingUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateBestLiftedWeightUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateExerciseDataUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateTrainingDataUseCase
import org.koin.dsl.module

fun useCasesModel() = module {
    single { UpdateBestLiftedWeightUseCase(exerciseDataSource = get()) }
    single { GetExerciseByTemplateIdUseCase(exerciseDatasource = get()) }
    single { GetOngoingTrainingUseCase(trainingHistoryDataSource = get()) }
    single { GetExerciseFromHistoryUseCase(exerciseHistoryDatasource = get()) }
    single { GetLatsSameExerciseUseCase(exerciseHistoryDatasource = get()) }
    single { UpdateExerciseDataUseCase(exerciseHistoryDatasource = get()) }
    single { UpdateTrainingDataUseCase(trainingHistoryDataSource = get()) }
}
