package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal

@Composable
fun SelectableGroups(
    groups: List<String>,
    modifier: Modifier = Modifier,
    selected: List<String>,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding16.dp),
        modifier = modifier
    ) {
        items(groups) { item ->
            SelectableGroupItem(item, selected, onClick)
        }
    }
}

@Composable
fun SelectableGroupItem(
    group: String,
    selected: List<String>,
    onClick: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = selected.contains(group),
            onClick = {
                onClick.invoke(group)
            }
        )) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(Dimens.selectTextOuterCircle.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .clip(CircleShape)
        ) {
            if (selected.contains(group))
                Box(
                    modifier = Modifier
                        .size(Dimens.selectTextInnerCircle.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .clip(CircleShape)
                )
        }
        Spacer(modifier = Modifier.padding(Dimens.Padding16.dp))
        TextMedium(
            text = group.uppercase(),
        )

        Spacer(Modifier.weight(1f))
        SharedRes.images.getImageByFileName(group)?.let {
            val painter: Painter =
                jp.mikhail.pankratov.trainingMate.core.data.painterResource(it)
            Image(
                painter = painter,
                contentDescription = "Group Image"
            )
        }
    }
}

@Composable
fun SelectableExercises(
    exerciseLocals: List<ExerciseLocal>,
    modifier: Modifier = Modifier,
    isSelected: List<String>,
    onClick: (ExerciseLocal) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding16.dp),
        modifier = modifier
    ) {
        items(exerciseLocals) { item ->
            SelectableExerciseItem(item, isSelected, onClick)
        }
    }
}

@Composable
fun SelectableExerciseItem(
    item: ExerciseLocal,
    isSelected: List<String>,
    onClick: (ExerciseLocal) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = isSelected.contains(item.name),
            onClick = {
                onClick.invoke(item)
            }
        )) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(Dimens.selectTextOuterCircle.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .clip(CircleShape)
        ) {
            if (isSelected.contains(item.name))
                Box(
                    modifier = Modifier
                        .size(Dimens.selectTextInnerCircle.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .clip(CircleShape)
                )
        }
        Spacer(modifier = Modifier.padding(Dimens.Padding16.dp))
        TextMedium(
            text = item.name.uppercase(),
        )

        Spacer(Modifier.weight(1f))
        SharedRes.images.getImageByFileName(item.image)?.let {
            val painter: Painter =
                jp.mikhail.pankratov.trainingMate.core.data.painterResource(it)
            Image(
                painter = painter,
                contentDescription = "Group Image"
            )
        }
    }
}