package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetTwoLastMonthlySummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetTwoLastWeeklySummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.InsetSummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.UpdateSummariesUseCase
import org.koin.core.Koin

class SummaryUseCaseProvider(val koin: Koin) {
    inline fun <reified T : Any> get(): T = koin.get()

    fun getTwoLastMonthlySummaryUseCase(): GetTwoLastMonthlySummaryUseCase = get()
    fun getTwoLastWeeklySummaryUseCase(): GetTwoLastWeeklySummaryUseCase = get()
    fun getInsetSummaryUseCase(): InsetSummaryUseCase = get()
    fun getUpdateSummariesUseCase(): UpdateSummariesUseCase = get()
}

