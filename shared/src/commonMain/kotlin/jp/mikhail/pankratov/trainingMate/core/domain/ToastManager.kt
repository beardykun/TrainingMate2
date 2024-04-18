package jp.mikhail.pankratov.trainingMate.core.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ToastManager {
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun showToast(message: String) {
        _message.value = message
    }

    fun clearMessage() {
        _message.value = null
    }
}
