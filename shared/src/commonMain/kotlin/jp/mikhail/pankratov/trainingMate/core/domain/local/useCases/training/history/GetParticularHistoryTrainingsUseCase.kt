package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.Flow

class GetParticularHistoryTrainingsUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    operator fun invoke(trainingTemplateId: Long): Flow<List<Training>> {
        return trainingHistoryDataSource.getParticularHistoryTrainings(trainingTemplateId = trainingTemplateId)
    }
}