package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local.TrainingHistoryDataSource
import kotlinx.coroutines.flow.Flow

class GetLatestHistoryTrainingsUseCase(private val trainingHistoryDataSource: TrainingHistoryDataSource) {
    operator fun invoke(): Flow<List<Training>> {
        return trainingHistoryDataSource.getLatestHistoryTrainings()
    }
}