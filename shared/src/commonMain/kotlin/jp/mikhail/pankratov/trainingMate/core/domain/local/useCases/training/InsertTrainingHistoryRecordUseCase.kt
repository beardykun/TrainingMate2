package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local.TrainingHistoryDataSource

class InsertTrainingHistoryRecordUseCase(private val trainingHistoryDataSource: TrainingHistoryDataSource) {
    suspend operator fun invoke(training: Training) {
        trainingHistoryDataSource.insertTrainingRecord(training)
    }
}