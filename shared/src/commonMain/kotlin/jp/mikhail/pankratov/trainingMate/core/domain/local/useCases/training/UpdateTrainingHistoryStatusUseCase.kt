package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource

class UpdateTrainingHistoryStatusUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    suspend operator fun invoke(trainingId: Long, status: String = "COMPLETED") {
        trainingHistoryDataSource.updateTainingHistoryStatus(trainingId = trainingId, status = status)
    }
}