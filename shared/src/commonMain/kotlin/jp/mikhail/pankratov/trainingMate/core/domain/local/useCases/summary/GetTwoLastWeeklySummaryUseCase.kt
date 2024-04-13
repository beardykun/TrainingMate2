package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary

import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import kotlinx.coroutines.flow.Flow

class GetTwoLastWeeklySummaryUseCase(private val summaryDatasource: ISummaryDatasource) {
    operator fun invoke(): Flow<List<WeeklySummary?>> {
        return summaryDatasource.getTwoLastWeeklySummary()
    }
}