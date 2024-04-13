package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.DeleteTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.DeleteTrainingTemplateUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.GetLatestHistoryTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.GetLocalTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.GetOngoingTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.InsertLocalTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.InsertTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.TrainingTableEmptyUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.UpdateTrainingHistoryStatusUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.UpdateTrainingLocalExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateTrainingHistoryDataUseCase
import org.koin.core.Koin

class TrainingUseCaseProvider(val koin: Koin) {
    inline fun <reified T : Any> get(): T = koin.get()

    //Local
    fun getInsertLocalTrainingUseCase(): InsertLocalTrainingUseCase = get()
    fun getTrainingTableEmptyUseCase(): TrainingTableEmptyUseCase = get()
    fun getLocalTrainingsUseCase(): GetLocalTrainingsUseCase = get()
    fun getUpdateTrainingLocalExerciseUseCase(): UpdateTrainingLocalExerciseUseCase = get()
    fun getDeleteTrainingTemplateUseCase(): DeleteTrainingTemplateUseCase = get()

    //History
    fun getInsertTrainingHistoryRecordUseCase(): InsertTrainingHistoryRecordUseCase = get()
    fun getLatestHistoryTrainingsUseCase(): GetLatestHistoryTrainingsUseCase = get()
    fun getDeleteTrainingHistoryRecordUseCase(): DeleteTrainingHistoryRecordUseCase = get()
    fun getOngoingTrainingUseCase(): GetOngoingTrainingUseCase = get()
    fun getUpdateTrainingHistoryDataUseCase(): UpdateTrainingHistoryDataUseCase = get()
    fun getUpdateTrainingHistoryStatusUseCase(): UpdateTrainingHistoryStatusUseCase = get()

}