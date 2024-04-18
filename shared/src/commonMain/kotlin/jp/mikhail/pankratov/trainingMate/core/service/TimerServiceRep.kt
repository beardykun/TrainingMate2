package jp.mikhail.pankratov.trainingMate.core.service

expect class TimerServiceRep {
    fun startService(initCount: Int)
    fun stopService()
}