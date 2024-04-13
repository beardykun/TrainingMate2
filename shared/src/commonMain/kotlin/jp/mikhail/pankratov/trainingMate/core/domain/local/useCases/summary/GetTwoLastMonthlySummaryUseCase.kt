package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary

import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import kotlinx.coroutines.flow.Flow

class GetTwoLastMonthlySummaryUseCase(private val summaryDatasource: ISummaryDatasource) {
    operator fun invoke(): Flow<List<MonthlySummary?>> {
        return summaryDatasource.getTwoLastMonthlySummary()
    }
}