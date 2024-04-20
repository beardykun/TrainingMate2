package jp.mikhail.pankratov.trainingMate.di.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.DeleteTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetLastSameHistoryTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetLatestHistoryTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetOngoingTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetParticularHistoryTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetParticularMonthHistoryTrainings
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetParticularWeekHistoryTrainings
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetTrainingHistoryRecordByIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.InsertTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.UpdateTrainingHistoryStatusUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.DeleteTrainingTemplateUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.GetLocalTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.InsertLocalTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.IsLocalTrainingExistsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.IsLocalTrainingTableEmptyUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.UpdateTrainingLocalExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateTrainingHistoryDataUseCase
import org.koin.dsl.module

fun trainingUseCaseModule() = module {
    single { TrainingUseCaseProvider(getKoin()) }
    //Local
    single { UpdateTrainingLocalExerciseUseCase(trainingDataSource = get()) }
    single { GetLocalTrainingsUseCase(trainingDataSource = get()) }
    single { DeleteTrainingTemplateUseCase(trainingDataSource = get()) }
    single { IsLocalTrainingTableEmptyUseCase(trainingDataSource = get()) }
    single { InsertLocalTrainingUseCase(trainingDataSource = get()) }
    single { IsLocalTrainingExistsUseCase(trainingDataSource = get()) }
    //History
    single { GetOngoingTrainingUseCase(trainingHistoryDataSource = get()) }
    single { UpdateTrainingHistoryDataUseCase(trainingHistoryDataSource = get()) }
    single { GetLatestHistoryTrainingsUseCase(trainingHistoryDataSource = get()) }
    single { DeleteTrainingHistoryRecordUseCase(trainingHistoryDataSource = get()) }
    single { InsertTrainingHistoryRecordUseCase(trainingHistoryDataSource = get()) }
    single { UpdateTrainingHistoryStatusUseCase(trainingHistoryDataSource = get()) }
    single { GetLastSameHistoryTrainingUseCase(trainingHistoryDataSource = get()) }
    single { GetTrainingHistoryRecordByIdUseCase(trainingHistoryDataSource = get()) }
    single { GetParticularHistoryTrainingsUseCase(trainingHistoryDataSource = get()) }
    single { GetParticularMonthHistoryTrainings(trainingHistoryDataSource = get()) }
    single { GetParticularWeekHistoryTrainings(trainingHistoryDataSource = get()) }
}