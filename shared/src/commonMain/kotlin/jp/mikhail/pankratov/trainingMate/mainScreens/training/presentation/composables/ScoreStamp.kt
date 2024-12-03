package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables

import Dimens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.score
import org.jetbrains.compose.resources.imageResource


@Composable
fun ScoreStamp(score: Long, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(160.dp)
            .clip(CircleShape)
            .background(Color.White) // Outer circle color
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
                radius = 25.dp.toPx(),
                center = Offset(size.width / 2, size.height / 2),
                alpha = .5f
            )
        }
        Text(
            text = "$score%",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
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

