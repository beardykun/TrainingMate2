package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import kotlinx.coroutines.delay

@Composable
fun ToastMessage(
    message: String,
    duration: Long = 3000L, // Duration in milliseconds
    onDismiss: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(duration)
        isVisible = false
        onDismiss()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }),
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.padding(Dimens.Padding16)
                .fillMaxSize(),
        ) {
            TextLarge(
                text = message,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 15))
                    .background(MaterialTheme.colors.primary)
                    .padding(Dimens.Padding16)
            )
        }
    }
}