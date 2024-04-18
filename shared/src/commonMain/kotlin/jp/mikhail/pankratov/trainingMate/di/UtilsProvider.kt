package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.NotificationUtils
import jp.mikhail.pankratov.trainingMate.core.domain.TimerServiceRep
import org.koin.core.Koin

class UtilsProvider(val koin: Koin) {

    inline fun <reified T : Any> get(): T = koin.get()

    fun getNotificationUtils(): NotificationUtils = get()
    fun getTimerServiceRep(): TimerServiceRep = get()
}