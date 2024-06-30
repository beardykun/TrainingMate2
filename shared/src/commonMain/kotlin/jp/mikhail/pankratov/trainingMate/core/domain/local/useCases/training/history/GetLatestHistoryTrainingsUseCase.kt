package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.Flow

class GetLatestHistoryTrainingsUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    operator fun invoke(limit: Long = 10, offset: Long = 0): Flow<List<Training>> {
        return trainingHistoryDataSource.getLatestHistoryTrainings(limit = limit, offset = offset)
    }
}