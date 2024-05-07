package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge

@Composable
fun TrainingComparison(
    lastTraining: Training?,
    ongoingTraining: Training?,
    trainingTime: String,
    onClick: (trainingId: Long) -> Unit
) {
    val textColor =
        if (
            (ongoingTraining?.totalLiftedWeight ?: 0.0) >= (lastTraining?.totalLiftedWeight ?: 0.0)
        ) Color.Blue
        else Color.Red
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = Dimens.Padding16, start = Dimens.Padding16, end = Dimens.Padding16)
            .clip(RoundedCornerShape(percent = 30))
            .padding(vertical = Dimens.Padding8)

    ) {
        lastTraining?.let { lastTraining ->
            Card(
                modifier = Modifier.weight(1f).padding(horizontal = Dimens.Padding8),
                elevation = CardDefaults.cardElevation(Dimens.cardElevation),
                onClick = {
                    lastTraining.id?.let { onClick.invoke(it) }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextLarge(
                        stringResource(
                            SharedRes.strings.last_training_lifted_weight,
                            lastTraining.totalLiftedWeight,
                            Utils.countTrainingTime(lastTraining)
                        ),
                        modifier = Modifier.padding(all = Dimens.Padding8)
                    )
                }
            }
        }
        ongoingTraining?.let { ongoingTraining ->
            Card(
                modifier = Modifier.weight(1f).padding(horizontal = Dimens.Padding8),
                elevation = CardDefaults.cardElevation(Dimens.cardElevation)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextLarge(
                        color = textColor,
                        text = stringResource(
                            SharedRes.strings.this_training_lifted_weight,
                            ongoingTraining.totalLiftedWeight,
                            trainingTime
                        ),
                        modifier = Modifier.padding(all = Dimens.Padding8)
                    )
                }
            }
        }
    }
}