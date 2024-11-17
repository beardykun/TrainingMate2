package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.getDrawableResourceByName
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.getExerciseNameStringResource
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.best_weight
import maxrep.shared.generated.resources.cd_done_icon
import maxrep.shared.generated.resources.group
import maxrep.shared.generated.resources.strength_defining
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExerciseItem(
    exerciseLocal: ExerciseLocal,
    onClick: (ExerciseLocal) -> Unit,
    isDone: Boolean = false,
    isStrengthDefining: Boolean,
    modifier: Modifier
) {
    val cardColor by animateColorAsState(
        targetValue = if (isDone)
            MaterialTheme.colorScheme.inversePrimary else
            CardDefaults.cardColors().containerColor
    )
    Card(
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        modifier = modifier.fillMaxSize()
            .padding(all = Dimens.Padding8)
            .clickable {
                onClick.invoke(exerciseLocal)
            },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Dimens.Padding8),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            getDrawableResourceByName(exerciseLocal.image)?.let {
                val painter = painterResource(it)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painter,
                        contentDescription = exerciseLocal.name,
                        modifier = Modifier.blur(if (isDone) 3.dp else 0.dp)
                    )
                    if (isDone) {
                        Icon(
                            imageVector = Icons.Outlined.Done,
                            contentDescription = Res.string.cd_done_icon.getString(),
                            modifier = Modifier.size(
                                Dimens.selectableGroupImageSize
                            )
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = Dimens.Padding16)) {
                TextLarge(
                    text = getExerciseNameStringResource(exerciseLocal.name).uppercase(),
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                TextMedium(
                    text = stringResource(
                        Res.string.group,
                        exerciseLocal.group.uppercase()
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.best_weight,
                        exerciseLocal.bestLiftedWeight
                    )
                )
                if (isStrengthDefining)
                    TextSmall(
                        Res.string.strength_defining.getString(),
                        fontWeight = FontWeight.SemiBold,
                        textColor = Color.Blue
                    )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}