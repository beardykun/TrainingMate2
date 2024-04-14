package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import kotlinx.coroutines.flow.Flow

class GetLocalTrainingsUseCase(private val trainingDataSource: ITrainingDataSource) {
    operator fun invoke(): Flow<List<TrainingLocal>> {
        return trainingDataSource.getLocalTrainings()
    }
}