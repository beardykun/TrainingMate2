package jp.mikhail.pankratov.trainingMate.exercise.presentation

import Dimens
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.getImageByFileName
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.data.painterResource
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium

@Composable
fun ExerciseItem(exerciseLocal: ExerciseLocal, onClick: (ExerciseLocal) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxSize()
            .padding(all = Dimens.Padding8.dp)
            .clickable {
            onClick.invoke(exerciseLocal)
        }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                TextMedium(text = "Group: ${exerciseLocal.group.uppercase()}")
                TextMedium(text = "Best weight: ${exerciseLocal.bestLiftedWeight} kg")
            }
        }
    }
}