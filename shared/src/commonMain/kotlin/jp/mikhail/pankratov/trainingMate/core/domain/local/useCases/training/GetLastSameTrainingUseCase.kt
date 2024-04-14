package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.Flow

class GetLastSameTrainingUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    operator fun invoke(trainingTemplateId: Long): Flow<Training?> {
        return trainingHistoryDataSource.getLastSameTraining(trainingTemplateId = trainingTemplateId)
    }
}