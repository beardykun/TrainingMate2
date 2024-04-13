package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.DeleteTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.DeleteTrainingTemplateUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.GetLatestHistoryTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.GetLocalTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.GetOngoingTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.InsertLocalTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.InsertTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.TrainingTableEmptyUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.UpdateTrainingLocalExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateTrainingHistoryDataUseCase
import org.koin.dsl.module

fun trainingUseCaseModule() = module {
    single { TrainingUseCaseProvider(getKoin()) }
    //Local
    single { UpdateTrainingLocalExerciseUseCase(trainingDataSource = get()) }
    single { GetLocalTrainingsUseCase(trainingDataSource = get()) }
    single { DeleteTrainingTemplateUseCase(trainingDataSource = get()) }
    single { TrainingTableEmptyUseCase(trainingDataSource = get()) }
    single { InsertLocalTrainingUseCase(trainingDataSource = get()) }
    //History
    single { GetOngoingTrainingUseCase(trainingHistoryDataSource = get()) }
    single { UpdateTrainingHistoryDataUseCase(trainingHistoryDataSource = get()) }
    single { GetLatestHistoryTrainingsUseCase(trainingHistoryDataSource = get()) }
    single { DeleteTrainingHistoryRecordUseCase(trainingHistoryDataSource = get()) }
    single { InsertTrainingHistoryRecordUseCase(trainingHistoryDataSource = get()) }

}