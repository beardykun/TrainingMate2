package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.data.painterResource
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium

@Composable
fun ExerciseItem(
    exerciseLocal: ExerciseLocal,
    onClick: (ExerciseLocal) -> Unit,
    isDone: Boolean = false,
    modifier: Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        modifier = modifier.fillMaxSize()
            .padding(all = Dimens.Padding8)
            .clickable {
                onClick.invoke(exerciseLocal)
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isDone) MaterialTheme.colorScheme.inversePrimary else CardDefaults.cardColors().containerColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Dimens.Padding8),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Padding16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SharedRes.images.getImageByFileName(exerciseLocal.image)?.let {
                val painter: Painter =
                    painterResource(it)

                Image(
                    painter = painter,
                    contentDescription = null
                )
            }

            Column {
                TextMedium(text = exerciseLocal.name)
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.group,
                        exerciseLocal.group.uppercase()
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.best_weight,
                        exerciseLocal.bestLiftedWeight
                    )
                )
            }
            if (isDone) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(Dimens.selectableGroupImageSize)
                        .background(Color.Black, shape = CircleShape)
                        .padding(Dimens.Padding4)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = SharedRes.strings.cd_done_icon.getString(),
                        tint = Color.Green,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}