package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

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
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        textAlign = textAlign,
        fontWeight = fontWeight,
        color = textColor,
        fontStyle = FontStyle.Italic,
        modifier = modifier
    )
}

@Composable
fun TextLarge(
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight,
        color = color,
        overflow = overflow,
        textAlign = textAlign,
        fontSize = fontSize,
        modifier = modifier
    )
}