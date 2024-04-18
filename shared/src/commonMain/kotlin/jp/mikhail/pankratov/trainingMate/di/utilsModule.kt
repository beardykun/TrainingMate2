package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.NotificationUtils
import jp.mikhail.pankratov.trainingMate.core.domain.TimerServiceRep
import org.koin.dsl.module

fun utilsModule(appModule: AppModule) = module {
    single<UtilsProvider> { UtilsProvider(getKoin()) }
    single<NotificationUtils> { appModule.notificationUtils }
    single<TimerServiceRep> { appModule.timerServiceRep }
}