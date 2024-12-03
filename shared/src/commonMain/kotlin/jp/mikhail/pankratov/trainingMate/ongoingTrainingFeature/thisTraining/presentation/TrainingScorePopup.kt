package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.ScoreStamp
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.ok
import maxrep.shared.generated.resources.training_score

@Composable
fun TrainingScorePopup(
    score: Int,
    comment: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)) // Dim background
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                // Prevent click-through
            },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(Dimens.Padding16)
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(Dimens.Padding16),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = Dimens.cardElevation
        ) {
            Column(
                modifier = Modifier.padding(Dimens.Padding24),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Res.string.training_score.getString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(Dimens.Padding16))

                ScoreStamp(score = score.toLong())
                Spacer(modifier = Modifier.height(Dimens.Padding16))

                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Dimens.Padding24))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.Padding8)
                ) {
                    Text(
                        text = Res.string.ok.getString(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

