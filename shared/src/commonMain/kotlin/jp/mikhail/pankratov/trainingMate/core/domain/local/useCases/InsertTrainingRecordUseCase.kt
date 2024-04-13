package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local.TrainingHistoryDataSource

class InsertTrainingRecordUseCase(private val trainingHistoryDataSource: TrainingHistoryDataSource) {
    suspend operator fun invoke(training: Training) {
        trainingHistoryDataSource.insertTrainingRecord(training)
    }
}