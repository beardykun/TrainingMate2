package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state

data class TimerState(
    val timerValue: Int = 60,
    val timer: Int = 60,
    val isExpanded: Boolean = false
)
