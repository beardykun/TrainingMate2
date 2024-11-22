package jp.mikhail.pankratov.trainingMate.di.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetTwoLastMonthlySummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetLastWeeklySummariesUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.InsetSummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.UpdateSummariesUseCase
import org.koin.dsl.module

fun summaryUseCaseModule() = module {
    single { SummaryUseCaseProvider(getKoin()) }

    single { GetTwoLastMonthlySummaryUseCase(summaryDatasource = get()) }
    single { GetLastWeeklySummariesUseCase(summaryDatasource = get()) }
    single { InsetSummaryUseCase(summaryDatasource = get()) }
    single { UpdateSummariesUseCase(summaryDatasource = get()) }
}