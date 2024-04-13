package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkEvent

@Composable
fun AnimatedTextSizeItem(
    set: ExerciseSet,
    targetSize: TextUnit = Dimens.normalTextSize,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    modifier: Modifier,
    isAnimating: Boolean
) {
    AnimatedVisibility(visible = set.weight.isNotBlank()) {
        // Remember animatable to animate font size
        val size = if (isAnimating) Dimens.animationTextSize.value else Dimens.normalTextSize.value
        val animatedFontSize = remember { Animatable(initialValue = size) }

        // Animate fontSize when this composable enters the composition
        LaunchedEffect(key1 = isAnimating) {
            if (isAnimating) {
                animatedFontSize.animateTo(
                    targetValue = targetSize.value,
                    animationSpec = tween(durationMillis = 2000) // 2 seconds animation
                )
                onEvent(ExerciseAtWorkEvent.OnAnimationSeen)
            }
        }
        val textColor = when (set.difficulty) {
            SetDifficulty.Light -> Color(0xFFE8F5E9)
            SetDifficulty.Medium -> Color(0xFFFFF9C4)
            SetDifficulty.Hard -> Color(0xFFFFEBEE)
        }

        Card(
            elevation = CardDefaults.cardElevation(Dimens.cardElevation),
            colors = CardDefaults.cardColors(containerColor = textColor),
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onEvent(ExerciseAtWorkEvent.OnDisplayDeleteDialog(true, set))
                })
            }.padding(all = Dimens.Padding16)

        ) {
            // Text composable with animated fontSize
            Box(modifier = Modifier.padding(all = Dimens.Padding8)) {
                Text(
                    text = "${set.weight} x ${set.reps}",
                    style = TextStyle(
                        fontSize = animatedFontSize.value.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}