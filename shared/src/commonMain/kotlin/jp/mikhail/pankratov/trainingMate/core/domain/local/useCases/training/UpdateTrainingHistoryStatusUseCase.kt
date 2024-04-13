package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource

class UpdateTrainingHistoryStatusUseCase(private val dataSource: ITrainingHistoryDataSource) {
    suspend operator fun invoke(trainingId: Long) {
        dataSource.updateStatus(trainingId = trainingId)
    }
}