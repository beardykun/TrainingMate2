package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import Dimens
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedTextSizeItem(text: String, targetSize: TextUnit = 16.sp, modifier: Modifier) {
    // Remember animatable to animate font size
    val animatedFontSize = remember { Animatable(initialValue = 50f) }

    // Animate fontSize when this composable enters the composition
    LaunchedEffect(key1 = text) {
        animatedFontSize.animateTo(
            targetValue = targetSize.value,
            animationSpec = tween(durationMillis = 2000) // 2 seconds animation
        )
    }

    Card(
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        modifier = modifier
            .padding(all = Dimens.Padding16)

    ) {

        // Text composable with animated fontSize
        Box(modifier = Modifier.padding(all = Dimens.Padding8)) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = animatedFontSize.value.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
                )
        }
    }
}