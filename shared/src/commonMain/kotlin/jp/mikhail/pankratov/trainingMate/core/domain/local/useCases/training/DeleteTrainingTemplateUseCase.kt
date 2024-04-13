package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local.TrainingDataSource

class DeleteTrainingTemplateUseCase(private val trainingDataSource: TrainingDataSource) {
    suspend operator fun invoke(trainingId: Long) {
        trainingDataSource.deleteTrainingTemplate(trainingId)
    }
}