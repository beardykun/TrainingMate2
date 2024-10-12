package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.style.TextOverflow
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.theme.goldLight
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.ExerciseListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> SelectableGroupVertical(
    items: List<T>,
    modifier: Modifier = Modifier,
    selected: List<T>,
    onClick: (T) -> Unit,
    displayItem: (T) -> String,
    listItem: @Composable (
        item: T,
        selected: List<T>,
        onClick: (T) -> Unit,
        modifier: Modifier
    ) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding16),
        modifier = modifier
    ) {
        this.items(
            items = items,
            key = { item ->
                displayItem(item)
            }) { item ->
            listItem(item, selected, onClick, modifier.animateItem())
        }
    }
}

@Composable
fun <T> SelectableGroupHorizontal(
    items: List<T>,
    modifier: Modifier = Modifier,
    selected: T,
    onClick: (T) -> Unit,
    displayItem: (T) -> String,
    listItem: @Composable (
        item: T,
        selected: T,
        onClick: (T) -> Unit
    ) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        this.items(
            items = items,
            key = { item ->
                displayItem(item)
            }) { item ->
            listItem(item, selected, onClick)
        }
    }
}

@Composable
fun SelectableGroupItem(
    group: String,
    isSelected: List<String>,
    onClick: (String) -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.selectable(
            selected = isSelected.contains(group),
            onClick = {
                onClick.invoke(group)
            }
        )) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(Dimens.selectTextOuterCircle)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .clip(CircleShape)
        ) {
            if (isSelected.contains(group))
                Box(
                    modifier = Modifier
                        .size(Dimens.selectTextInnerCircle)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .clip(CircleShape)
                )
        }
        Spacer(modifier = Modifier.padding(Dimens.Padding16))
        TextMedium(
            text = group.uppercase(),
        )

        Spacer(Modifier.weight(1f))
        SharedRes.images.getImageByFileName(group)?.let {
            val painter: Painter =
                jp.mikhail.pankratov.trainingMate.core.data.painterResource(it)
            Image(
                painter = painter,
                contentDescription = stringResource(SharedRes.strings.cd_group_image)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectableExercises(
    exerciseLocals: List<ExerciseListItem>,
    modifier: Modifier = Modifier,
    isSelected: List<String>,
    onClick: (ExerciseLocal) -> Unit,
    onDeleteClick: (ExerciseLocal) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding16),
        modifier = modifier
    ) {
        items(
            items = exerciseLocals,
            key = { item ->
                when (item) {
                    is ExerciseListItem.Header -> item.muscleGroup
                    is ExerciseListItem.ExerciseItem -> item.exercise.name
                }
            }) { item ->
            when (item) {
                is ExerciseListItem.Header -> {
                    TextLarge(
                        text = item.muscleGroup.uppercase(),
                        modifier = Modifier.clip(RoundedCornerShape(25))
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                            .padding(Dimens.Padding4)
                    )
                }

                is ExerciseListItem.ExerciseItem -> {
                    SelectableExerciseItem(
                        item.exercise,
                        isSelected,
                        onClick,
                        onDeleteClick,
                        modifier.animateItem()
                    )
                }
            }
        }
    }
}


@Composable
fun SelectableExerciseItem(
    item: ExerciseLocal,
    isSelected: List<String>,
    onClick: (ExerciseLocal) -> Unit,
    onDeleteClick: (ExerciseLocal) -> Unit,
    modifier: Modifier
) {
    val color = if (item.isStrengthDefining) goldLight else Color.Unspecified
    Card(
        shape = RoundedCornerShape(percent = 20),
        border = BorderStroke(width = Dimens.borderWidth, color = Color.Black),
        elevation = CardDefaults.elevatedCardElevation(Dimens.cardElevation),
        colors = CardDefaults.cardColors().copy(containerColor = color),
        modifier = modifier
            .selectable(
                selected = isSelected.contains(item.name),
                onClick = {
                    onClick.invoke(item)
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = Dimens.Padding16, vertical = Dimens.Padding8)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(Dimens.selectTextOuterCircle)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .clip(CircleShape)
            ) {
                if (isSelected.contains(item.name))
                    Box(
                        modifier = Modifier
                            .size(Dimens.selectTextInnerCircle)
                            .clip(CircleShape)
                            .background(color = Color.White)
                            .clip(CircleShape)
                    )
            }
            Spacer(modifier = Modifier.padding(Dimens.Padding16))
            Column(modifier = Modifier.weight(1f)) {
                TextMedium(
                    text = item.name.uppercase(),
                    maxLines = 1,  // Ensure text stays on one line
                    overflow = TextOverflow.Ellipsis  // Add ellipsis if text is too long
                )
                if (item.isStrengthDefining)
                    TextSmall(text = SharedRes.strings.strength_defining.getString())
            }

            Spacer(Modifier.width(Dimens.Padding16)) // Use width here to give consistent spacing
            SharedRes.images.getImageByFileName(item.image)?.let {
                val painter: Painter =
                    jp.mikhail.pankratov.trainingMate.core.data.painterResource(it)
                Image(
                    painter = painter,
                    contentDescription = stringResource(SharedRes.strings.cd_group_image),
                    modifier = Modifier.size(Dimens.selectableGroupImageSize)  // Define a consistent size if necessary
                )
            }

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onDeleteClick.invoke(item)
                    }
                    .padding(start = Dimens.Padding8)  // Optional padding for space between image and icon
                    .size(Dimens.standardIcon)  // Define a consistent size for the icon
            )
        }
    }
}