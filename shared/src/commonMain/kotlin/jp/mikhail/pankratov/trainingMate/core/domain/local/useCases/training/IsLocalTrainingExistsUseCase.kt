package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource

class IsLocalTrainingExistsUseCase(private val trainingDataSource: ITrainingDataSource) {
    suspend operator fun invoke(trainingName: String): Boolean {
        return trainingDataSource.isLocalTrainingExists(trainingName)
    }
}