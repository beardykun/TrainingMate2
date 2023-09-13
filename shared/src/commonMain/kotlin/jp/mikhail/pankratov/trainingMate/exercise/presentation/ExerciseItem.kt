package jp.mikhail.pankratov.trainingMate.exercise.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium

@Composable
fun ExerciseItem(name: String, group: String, image: String) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SharedRes.images.getImageByFileName(image)?.let {
                val painter: Painter =
                    jp.mikhail.pankratov.trainingMate.core.data.painterResource(it)

                Image(
                    painter = painter,
                    contentDescription = null
                )
            }

            Column {
                TextMedium(text = name)
                TextMedium(text = group)
            }
        }
    }
}