package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state

data class TimerState(
    val timerValue: Int = 60,
    val timerMin: Int = 1,
    val timerSec: Int = 0,
    val isExpanded: Boolean = false,
    val isCounting: Boolean = false
)
