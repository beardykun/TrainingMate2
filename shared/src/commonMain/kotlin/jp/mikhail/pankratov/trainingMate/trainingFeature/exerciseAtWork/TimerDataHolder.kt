package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// In the shared module
object TimerDataHolder {
    private val _timerValue = MutableStateFlow<Int?>(null)
    val timerValue: StateFlow<Int?> = _timerValue.asStateFlow()

    fun postValue(time: Int) {
        _timerValue.value = time
    }
}
