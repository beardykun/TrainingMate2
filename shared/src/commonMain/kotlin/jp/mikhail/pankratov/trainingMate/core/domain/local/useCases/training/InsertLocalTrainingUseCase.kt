package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource


class InsertLocalTrainingUseCase(private val trainingDataSource: ITrainingDataSource) {
    suspend operator fun invoke(trainingLocal: TrainingLocal) {
        trainingDataSource.insertTraining(trainingLocal)
    }
}