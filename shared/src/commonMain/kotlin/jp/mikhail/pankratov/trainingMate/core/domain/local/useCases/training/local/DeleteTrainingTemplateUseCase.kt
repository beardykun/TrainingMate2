package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource

class DeleteTrainingTemplateUseCase(private val trainingDataSource: ITrainingDataSource) {
    suspend operator fun invoke(trainingId: Long) {
        trainingDataSource.deleteTrainingTemplate(trainingId)
    }
}