package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource

class DeleteTrainingHistoryRecordUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    suspend operator fun invoke(trainingId: Long) {
        trainingHistoryDataSource.deleteTrainingRecord(trainingId = trainingId)
    }
}