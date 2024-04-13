package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextMedium(
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Color.Unspecified,
    maxLines: Int = 10,
    textAlign: TextAlign = TextAlign.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

@Composable
fun TextSmall(
    text: String,
    textAlign: TextAlign = TextAlign.Unspecified,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun TextLarge(
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Unspecified,
    color: Color = Color.Unspecified,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}