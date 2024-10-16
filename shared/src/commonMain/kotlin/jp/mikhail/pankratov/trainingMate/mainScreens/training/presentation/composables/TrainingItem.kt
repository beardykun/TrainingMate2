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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.times
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.domain.util.getDrawableResourceByName
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.stringToList
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.cd_delete
import maxrep.shared.generated.resources.cd_muscle_group_image
import maxrep.shared.generated.resources.exercises_with_new_line
import maxrep.shared.generated.resources.groups
import maxrep.shared.generated.resources.total_lifted_weight_with_args
import maxrep.shared.generated.resources.training_duration_with_arg
import maxrep.shared.generated.resources.training_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocalTrainingItem(
    training: TrainingLocal,
    onClick: () -> Unit,
    onDeleteClick: (id: Long) -> Unit,
    isDeletable: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.inversePrimary,
    contentColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        modifier = modifier
            .clickable { onClick.invoke() }
            .padding(Dimens.Padding8)
            .fillMaxWidth().padding(horizontal = Dimens.Padding8)
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
                    text = stringResource(Res.string.training_name),
                    color = contentColor
                )
                if (isDeletable) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = Color.Red,
                        contentDescription = stringResource(Res.string.cd_delete),
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
            TextLarge(text = stringResource(Res.string.groups), color = contentColor)
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
                    text = stringResource(Res.string.training_name),
                    color = contentColor
                )
                Icon(
                    imageVector = Icons.Filled.Delete,
                    tint = Color.Red,
                    contentDescription = stringResource(Res.string.cd_delete),
                    modifier = Modifier.clickable {
                        training.id?.let { onDeleteClick.invoke(it) }
                    })
            }
            TextLarge(text = training.name.uppercase(), color = contentColor)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            val exercises = training.doneExercises.ifEmpty { training.exercises }
            TextLarge(
                text = stringResource(Res.string.exercises_with_new_line) + exercises.toString()
                    .substring(1, exercises.toString().length - 1), color = contentColor
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(
                    Res.string.total_lifted_weight_with_args,
                    training.totalLiftedWeight
                ),
                color = contentColor
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(
                    Res.string.training_duration_with_arg, Utils.trainingLengthToMin(
                        training
                    ).toString()
                ), color = contentColor
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(text = stringResource(Res.string.groups), color = contentColor)
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
            getDrawableResourceByName(image)?.let {
                val painter = painterResource(it)
                Image(
                    painter = painter,
                    contentDescription = stringResource(Res.string.cd_muscle_group_image),
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