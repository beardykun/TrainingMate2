package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.service.TimerServiceRep
import org.koin.dsl.module

fun utilsModule(appModule: AppModule) = module {
    single<UtilsProvider> { UtilsProvider(getKoin()) }
    single<TimerServiceRep> { appModule.timerServiceRep }
}