package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CommonButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = Dimens.Padding48, vertical = Dimens.Padding16)
            .height(Dimens.Padding64),
        enabled = enabled
    ) {
        TextLarge(
            text = text,
            color = Color.White
        )
    }
}