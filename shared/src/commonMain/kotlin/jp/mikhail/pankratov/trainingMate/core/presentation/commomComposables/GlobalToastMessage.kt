package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import jp.mikhail.pankratov.trainingMate.core.domain.ToastManager

@Composable
fun GlobalToastMessage() {
    val message by ToastManager.message.collectAsState()

    message?.let {
        ToastMessage(
            message = it,
            onDismiss = { ToastManager.clearMessage() }
        )
    }
}
