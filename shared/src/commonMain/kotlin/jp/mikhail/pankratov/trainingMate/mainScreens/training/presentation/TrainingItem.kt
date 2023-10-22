package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import Dimens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.stringToList

@Composable
fun LocalTrainingItem(training: TrainingLocal, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(Dimens.Padding8.dp)
            .clip(RoundedCornerShape(percent = 15))
            .widthIn(min = 100.dp, max = 300.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inversePrimary)
                .fillMaxSize()
                .padding(Dimens.Padding16.dp)
        ) {
            TextLarge(text = stringResource(SharedRes.strings.training_name))
            TextLarge(text = training.name.uppercase())
            Spacer(modifier = Modifier.height(Dimens.Padding8.dp))
            val groupsText =
                stringResource(SharedRes.strings.groups) + training.groups.uppercase()
            TextLarge(text = groupsText)
            Spacer(modifier = Modifier.height(Dimens.Padding8.dp))
            OverlappingImagesBackground(
                groups = training.groups.stringToList()
            )
        }
    }
}

@Composable
fun TrainingItem(training: Training, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(Dimens.Padding8.dp)
            .clip(RoundedCornerShape(percent = 15))
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inversePrimary)
                .fillMaxSize()
                .padding(Dimens.Padding16.dp)

        ) {
            TextLarge(text = stringResource(SharedRes.strings.training_name))
            TextMedium(text = training.name.uppercase())
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextLarge(text = stringResource(SharedRes.strings.groups))
                TextMedium(text = training.groups.uppercase())
            }
            TextMedium(text = "Exercises: " + training.exercises.toString())
            TextMedium(text = "Total lifted weight: " + training.totalWeightLifted.toString())
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