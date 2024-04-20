package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource

class IsLocalTrainingTableEmptyUseCase(private val trainingDataSource: ITrainingDataSource) {
    suspend operator fun invoke(): Boolean {
        return trainingDataSource.isLocalTrainingTableEmpty()
    }
}