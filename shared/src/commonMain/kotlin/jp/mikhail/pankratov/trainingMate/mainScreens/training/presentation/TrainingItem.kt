package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import Dimens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.stringToList

@Composable
fun TrainingItem(training: Training, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Padding16.dp)
            .clip(RoundedCornerShape(Dimens.Padding24.dp))
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(Dimens.Padding16.dp)
            .clickable { onClick.invoke() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TextMedium(text = "Training name:")
            TextMedium(text = training.name)
            TextMedium(text = "Groups:")
            TextMedium(text = training.groups)
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
                    contentDescription = "muscle group image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(
                            x = index * 30.dp
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