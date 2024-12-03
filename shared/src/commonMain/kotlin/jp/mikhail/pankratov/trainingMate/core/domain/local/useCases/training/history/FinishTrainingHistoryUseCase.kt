package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource

class FinishTrainingHistoryUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    suspend operator fun invoke(trainingId: Long, status: String = "COMPLETED", score: Int) {
        trainingHistoryDataSource.finishTrainingHistory(trainingId = trainingId, status = status, score = score)
    }
}