package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables

import Dimens
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.score
import org.jetbrains.compose.resources.imageResource


@Composable
fun ScoreStamp(score: Long, modifier: Modifier = Modifier) {
    var isStamped by remember { mutableStateOf(false) }

    // Trigger the animation on first composition
    LaunchedEffect(Unit) {
        isStamped = true
    }

    val scale by animateFloatAsState(
        targetValue = if (isStamped) 1f else 3f, // Start big and scale down
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )

    val alpha by animateFloatAsState(
        targetValue = if (isStamped) 1f else 0f, // Fade in
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 250
        )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(Dimens.scoreStampSize)
            .scale(scale)
            .alpha(alpha)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        val image = imageResource(Res.drawable.score)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val circleDiameter = size.minDimension

            // Calculate the destination size and position for the image to fit within the circle
            val dstSize = IntSize(circleDiameter.toInt(), circleDiameter.toInt())
            val dstOffset = IntOffset(
                ((size.width - dstSize.width) / 2).toInt(),
                ((size.height - dstSize.height) / 2).toInt()
            )
            drawImage(
                image = image,
                dstOffset = dstOffset,
                dstSize = dstSize
            )
            drawCircle(
                color = Color.Black,
                radius = Dimens.scoreTextBackgroundRadius.toPx(),
                center = Offset(size.width / 2, size.height / 2),
                alpha = .5f
            )
        }
        Text(
            text = "$score%",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = Dimens.largeTextSize,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(4f, 4f), // Offset for the shadow
                    blurRadius = 4f // Blur radius for a smooth shadow
                )
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .rotate(-15f)
                .padding(Dimens.Padding8) // Add padding inside the circle
        )
    }
}

