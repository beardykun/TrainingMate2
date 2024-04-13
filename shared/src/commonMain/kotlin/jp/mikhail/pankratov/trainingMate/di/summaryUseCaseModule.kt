package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetTwoLastMonthlySummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetTwoLastWeeklySummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.InsetSummaryUseCase
import org.koin.dsl.module

fun summaryUseCaseModule() = module {
    single { SummaryUseCaseProvider(getKoin()) }

    single { GetTwoLastMonthlySummaryUseCase(summaryDatasource = get()) }
    single { GetTwoLastWeeklySummaryUseCase(summaryDatasource = get()) }
    single { InsetSummaryUseCase(summaryDatasource = get()) }
}