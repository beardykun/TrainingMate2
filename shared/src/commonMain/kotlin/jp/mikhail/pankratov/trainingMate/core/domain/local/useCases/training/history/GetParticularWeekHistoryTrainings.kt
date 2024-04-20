package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.Flow

class GetParticularWeekHistoryTrainings(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    operator fun invoke(year: Long, weekNum: Long): Flow<List<Training>> {
        return trainingHistoryDataSource.getParticularWeekTraining(year = year, weekNum = weekNum)
    }
}