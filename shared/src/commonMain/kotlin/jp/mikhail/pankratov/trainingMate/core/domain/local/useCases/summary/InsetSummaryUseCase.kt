package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary

import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource

class InsetSummaryUseCase(private val summaryDatasource: ISummaryDatasource) {
    suspend operator fun invoke() {
        summaryDatasource.insetSummary()
    }
}