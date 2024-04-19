package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.sp

@Composable
fun CountdownAnimation(
    currentTimerValue: Int,
) {
    // This will hold the current scale of the animation.
    val scale: Float by animateFloatAsState(
        targetValue = 1f / (currentTimerValue * 0.1f),
        animationSpec = tween(
            durationMillis = 500, // duration of the animation
            easing = LinearEasing
        )
    )

    // We display the countdown text, making sure it's in the middle of the screen.
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = if (currentTimerValue > 0) currentTimerValue.toString() else "",
            fontSize = 120.sp, // or whatever size is appropriate
            modifier = Modifier.scale(scale) // applying the scale modifier
        )
    }
}