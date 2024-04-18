package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.service.TimerServiceRep
import org.koin.core.Koin

class UtilsProvider(val koin: Koin) {

    inline fun <reified T : Any> get(): T = koin.get()
    fun getTimerServiceRep(): TimerServiceRep = get()
}