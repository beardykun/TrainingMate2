package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary

import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import kotlinx.coroutines.flow.Flow

class GetLastWeeklySummariesUseCase(private val summaryDatasource: ISummaryDatasource) {
    operator fun invoke(limit: Long = 2): Flow<List<WeeklySummary?>> {
        return summaryDatasource.getLastWeeklySummaries(limit = limit)
    }
}