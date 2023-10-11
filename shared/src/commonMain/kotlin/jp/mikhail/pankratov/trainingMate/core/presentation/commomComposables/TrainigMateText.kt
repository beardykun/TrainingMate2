package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TextMedium(text: String, fontWeight: FontWeight = FontWeight.Bold, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = fontWeight,
        modifier = modifier
    )
}

@Composable
fun TextSmall(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
    )
}

@Composable
fun TextLarge(text: String, fontWeight: FontWeight = FontWeight.Bold, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight,
        color = Color.Black,
        modifier = modifier
    )
}