package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource

class TrainingTableEmptyUseCase(private val trainingDataSource: ITrainingDataSource) {
    suspend operator fun invoke() : Boolean {
        return trainingDataSource.trainingTableEmpty()
    }
}