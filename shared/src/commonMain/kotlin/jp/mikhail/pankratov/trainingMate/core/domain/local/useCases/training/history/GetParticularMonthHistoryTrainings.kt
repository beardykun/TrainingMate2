package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.Flow

class GetParticularMonthHistoryTrainings(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    operator fun invoke(year: Long, monthNum: Long): Flow<List<Training>> {
        return trainingHistoryDataSource.getParticularMonthTraining(year = year, monthNum = monthNum)
    }
}