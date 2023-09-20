package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun SelectableGroup(
    groups: List<String>,
    modifier: Modifier = Modifier,
    isSelected: List<String>,
    onClick: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding16.dp),
        modifier = modifier
    ) {
        groups.forEach { item ->
            SelectableItem(item, isSelected, onClick)
        }
    }
}

@Composable
fun SelectableItem(
    group: String,
    isSelected: List<String>,
    onClick: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = isSelected.contains(group),
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
            if (isSelected.contains(group))
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