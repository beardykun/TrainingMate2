package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.Flow

class GetHistorySearchResultsUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {

    operator fun invoke(query: String): Flow<List<Training>> {
        return trainingHistoryDataSource.getSearchResults(query)
    }
}