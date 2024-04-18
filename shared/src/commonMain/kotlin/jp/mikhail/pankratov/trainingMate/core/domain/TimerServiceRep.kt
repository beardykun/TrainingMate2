package jp.mikhail.pankratov.trainingMate.core.domain

expect class TimerServiceRep {
    fun startService(initCount: Int)
    fun stopService()
}