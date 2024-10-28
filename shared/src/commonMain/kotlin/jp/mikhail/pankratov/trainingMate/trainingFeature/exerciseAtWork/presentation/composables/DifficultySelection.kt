package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.hard
import maxrep.shared.generated.resources.light
import maxrep.shared.generated.resources.medium

@Composable
fun DifficultySelection(
    selected: String,
    onSelect: (SetDifficulty) -> Unit
) {
    val light = Res.string.light.getString()
    val medium = Res.string.medium.getString()
    val hard = Res.string.hard.getString()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Padding16)
    ) {
        val textModifier = Modifier
            .weight(1f)
            .clip(shape = RoundedCornerShape(percent = 25))
        TextMedium(
            text = light,
            textAlign = TextAlign.Center,
            modifier = textModifier
                .background(
                    color = if (selected == SetDifficulty.Light.name) Color.Green
                    else Color.Unspecified
                )
                .clickable {
                    onSelect.invoke(SetDifficulty.Light)
                }
                .padding(vertical = Dimens.Padding8)
        )
        TextMedium(
            text = medium,
            textAlign = TextAlign.Center,
            modifier = textModifier
                .background(
                    color = if (selected == SetDifficulty.Medium.name) Color.Yellow
                    else Color.Unspecified
                )
                .clickable {
                    onSelect.invoke(SetDifficulty.Medium)
                }
                .padding(vertical = Dimens.Padding8)
        )
        TextMedium(
            text = hard,
            textAlign = TextAlign.Center,
            modifier = textModifier
                .background(
                    color = if (selected == SetDifficulty.Hard.name) Color.Red
                    else Color.Unspecified
                )
                .clickable {
                    onSelect.invoke(SetDifficulty.Hard)
                }
                .padding(vertical = Dimens.Padding8)
        )
    }
}