package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.presentation

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
import androidx.compose.ui.text.font.FontWeight
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.Utils
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.last_training_with_args
import maxrep.shared.generated.resources.this_exercise_length
import maxrep.shared.generated.resources.this_exercise_lifted
import maxrep.shared.generated.resources.this_training
import org.jetbrains.compose.resources.stringResource

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
                    TextMedium(
                        text = stringResource(
                            resource = Res.string.last_training_with_args,
                            lastTraining.totalLiftedWeight,
                            Utils.countTrainingTime(lastTraining)
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(all = Dimens.Padding8)
                    )
                }
            }
        }
        ongoingTraining?.let { ongoingTraining ->
            val weightTextColor =
                if (ongoingTraining.totalLiftedWeight >= (lastTraining?.totalLiftedWeight
                        ?: 0.0)
                ) Color.Blue else Color.Red
            Card(
                modifier = Modifier.weight(1f).padding(horizontal = Dimens.Padding8),
                elevation = CardDefaults.cardElevation(Dimens.cardElevation)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextMedium(
                        text = Res.string.this_training.getString(),
                        arguments = arrayOf(
                            Pair(
                                Res.string.this_exercise_lifted.getString(),
                                Color.Unspecified
                            ),
                            Pair(
                                ongoingTraining.totalLiftedWeight.toString(),
                                weightTextColor
                            ),
                            Pair(
                                Res.string.this_exercise_length.getString(),
                                Color.Unspecified
                            ),
                            Pair(
                                trainingTime,
                                Color.Unspecified
                            )
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(all = Dimens.Padding8)
                    )
                }
            }
        }
    }
}