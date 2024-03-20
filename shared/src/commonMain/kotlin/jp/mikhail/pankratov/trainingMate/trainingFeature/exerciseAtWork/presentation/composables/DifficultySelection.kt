package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

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
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge

@Composable
fun DifficultySelection(
    selected: String,
    onSelect: (String) -> Unit
) {
    val light = SharedRes.strings.light.getString()
    val medium = SharedRes.strings.medium.getString()
    val hard = SharedRes.strings.hard.getString()
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = Dimens.Padding16),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Padding16)
    ) {
        val textModifier = Modifier
            .weight(1f)
            .clip(shape = RoundedCornerShape(percent = 25))
        TextLarge(
            text = light,
            textAlign = TextAlign.Center,
            modifier = textModifier
                .background(
                    color = if (selected == SetDifficulty.Light.name) Color.Green
                    else Color.Unspecified
                )
                .clickable {
                    onSelect.invoke(SetDifficulty.Light.name)
                }
                .padding(vertical = Dimens.Padding8)
        )
        TextLarge(
            text = medium,
            textAlign = TextAlign.Center,
            modifier = textModifier
                .background(
                    color = if (selected == SetDifficulty.Medium.name) Color.Yellow
                    else Color.Unspecified
                )
                .clickable {
                    onSelect.invoke(SetDifficulty.Medium.name)
                }
                .padding(vertical = Dimens.Padding8)
        )
        TextLarge(
            text = hard,
            textAlign = TextAlign.Center,
            modifier = textModifier
                .background(
                    color = if (selected == SetDifficulty.Hard.name) Color.Red
                    else Color.Unspecified
                )
                .clickable {
                    onSelect.invoke(SetDifficulty.Hard.name)
                }
                .padding(vertical = Dimens.Padding8)
        )
    }
}