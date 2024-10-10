package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables

import Dimens
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.times
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.stringToList

@Composable
fun LocalTrainingItem(
    training: TrainingLocal,
    onClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    isDeletable: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.inversePrimary,
    contentColor: Color = Color.Black,
    limitWidth: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        modifier = modifier
            .clickable { onClick.invoke() }
            .padding(Dimens.Padding8)
            .then(
                if (limitWidth)
                    modifier.widthIn(
                        min = Dimens.cardMinWidth,
                        max = Dimens.cardMaxWidth
                    ) else modifier.fillMaxWidth().padding(horizontal = Dimens.Padding8)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(containerColor)
                .padding(Dimens.Padding16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextLarge(
                    text = stringResource(SharedRes.strings.training_name),
                    color = contentColor
                )
                if (isDeletable) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = Color.Red,
                        contentDescription = stringResource(SharedRes.strings.cd_delete),
                        modifier = Modifier.clickable {
                            training.id?.let { onDeleteClick.invoke(it) }
                        })
                }
            }
            TextLarge(text = training.name.uppercase(), color = contentColor)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            AnimatedVisibility(visible = training.description.isNotBlank()) {
                TextLarge(text = training.description, color = contentColor)
                Spacer(modifier = Modifier.height(Dimens.Padding8))
            }
            TextLarge(text = stringResource(SharedRes.strings.groups), color = contentColor)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            OverlappingImagesBackground(
                groups = training.groups.stringToList()
            )
        }
    }
}

@Composable
fun TrainingItem(
    training: Training,
    onClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    containerColor: Color = Color.Unspecified,
    contentColor: Color = Color.Unspecified
) {
    Card(
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        modifier = Modifier
            .padding(Dimens.Padding8)
            .clickable { onClick.invoke() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(containerColor)
                .padding(Dimens.Padding16)

        ) {
            training.startTime?.let {
                TextLarge(
                    text = Utils.formatEpochMillisToDate(it),
                    color = contentColor
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextLarge(
                    text = stringResource(SharedRes.strings.training_name),
                    color = contentColor
                )
                Icon(
                    imageVector = Icons.Filled.Delete,
                    tint = Color.Red,
                    contentDescription = stringResource(SharedRes.strings.cd_delete),
                    modifier = Modifier.clickable {
                        training.id?.let { onDeleteClick.invoke(it) }
                    })
            }
            TextLarge(text = training.name.uppercase(), color = contentColor)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(SharedRes.strings.exercises_with_new_line) + training.exercises.toString()
                    .substring(1, training.exercises.toString().length - 1), color = contentColor
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(
                    SharedRes.strings.total_lifted_weight_with_args,
                    training.totalLiftedWeight
                ),
                color = contentColor
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(
                    SharedRes.strings.training_duration_with_arg, Utils.trainingLengthToMin(
                        training
                    ).toString()
                ), color = contentColor
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(text = stringResource(SharedRes.strings.groups), color = contentColor)
            OverlappingImagesBackground(
                groups = training.groups.stringToList()
            )
        }
    }
}

@Composable
fun OverlappingImagesBackground(groups: List<String>, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        groups.forEachIndexed { index, image ->
            SharedRes.images.getImageByFileName(image)?.let {
                val painter: Painter =
                    jp.mikhail.pankratov.trainingMate.core.data.painterResource(it)
                Image(
                    painter = painter,
                    contentDescription = stringResource(SharedRes.strings.cd_muscle_group_image),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(
                            x = index * Dimens.mediumIconSize
                        )
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f))
        )
    }
}