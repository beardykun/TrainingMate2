package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource

class InsertTrainingHistoryRecordUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    suspend operator fun invoke(training: Training) {
        trainingHistoryDataSource.insertTrainingRecord(training)
    }
}