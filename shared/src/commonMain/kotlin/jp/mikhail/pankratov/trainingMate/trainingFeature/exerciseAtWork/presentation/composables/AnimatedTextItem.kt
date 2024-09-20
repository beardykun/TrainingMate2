package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkEvent

@Composable
fun AnimatedTextItem(
    lastTrainingSet: ExerciseSet?,
    set: ExerciseSet,
    targetSize: TextUnit = Dimens.normalTextSize,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    modifier: Modifier,
    isAnimating: Boolean
) {
    AnimatedVisibility(visible = set.weight.isNotBlank()) {
        val size = if (isAnimating) Dimens.animationTextSize.value else Dimens.normalTextSize.value
        val animatedFontSize = remember { Animatable(initialValue = size) }

        LaunchedEffect(key1 = isAnimating) {
            if (isAnimating) {
                animatedFontSize.animateTo(
                    targetValue = targetSize.value,
                    animationSpec = tween(durationMillis = 1000)
                )
                onEvent(ExerciseAtWorkEvent.OnAnimationSeen)
            }
        }
        val textColor = Utils.setDifficultyColor(set.difficulty)
        var expanded by remember { mutableStateOf(false) }

        Card(
            elevation = CardDefaults.cardElevation(Dimens.cardElevation),
            colors = CardDefaults.cardColors(containerColor = textColor),
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onEvent(ExerciseAtWorkEvent.OnDisplayDeleteDialog(true, set))
                    },
                    onTap = { expanded = true })
            }.padding(all = Dimens.Padding16)

        ) {
            Column(
                modifier = Modifier
                    .padding(all = Dimens.Padding8)
            ) {
                Box(modifier = modifier) {
                    Text(
                        text = "${set.weight} kg x ${set.reps}",
                        style = TextStyle(
                            fontSize = animatedFontSize.value.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Column(modifier = Modifier.padding(Dimens.Padding8)) {
                            Text(
                                text = stringResource(
                                    SharedRes.strings.last_training_set,
                                    lastTrainingSet?.weight ?: "",
                                    lastTrainingSet?.reps ?: ""
                                )
                            )
                            lastTrainingSet?.restTimeText?.let {
                                TextSmall(
                                    text = stringResource(SharedRes.strings.interval, it)
                                )
                            }
                        }
                    }
                }
                set.restTimeText?.let {
                    TextSmall(
                        text = stringResource(SharedRes.strings.interval, it)
                    )
                }
            }
        }
    }
}