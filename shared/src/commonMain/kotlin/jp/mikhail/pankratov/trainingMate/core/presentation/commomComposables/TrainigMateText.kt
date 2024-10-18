package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextMedium(
    text: String,
    vararg arguments: Pair<String, Color>,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Unspecified,
    maxLines: Int = 10,
    textAlign: TextAlign = TextAlign.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.prepareText(arguments = arguments),
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
fun TextMedium(
    text: AnnotatedString,
    fontWeight: FontWeight = FontWeight.Normal,
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
    vararg arguments: Pair<String, Color>,
    textAlign: TextAlign = TextAlign.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.prepareText(arguments = arguments),
        style = MaterialTheme.typography.bodySmall,
        textAlign = textAlign,
        fontWeight = fontWeight,
        color = textColor,
        fontStyle = FontStyle.Italic,
        modifier = modifier
    )
}

@Composable
fun TextSmall(
    text: AnnotatedString,
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
    vararg arguments: Pair<String, Color>,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.prepareText(arguments = arguments),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight,
        color = color,
        overflow = overflow,
        textAlign = textAlign,
        fontSize = fontSize,
        modifier = modifier
    )
}

@Composable
fun TextLarge(
    text: AnnotatedString,
    fontWeight: FontWeight = FontWeight.Normal,
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

fun String.prepareText(
    vararg arguments: Pair<String, Color>
) = buildAnnotatedString {
    append(this@prepareText)
    arguments.forEach { (argument, color) ->
        withStyle(style = SpanStyle(color = color)) {
            append(argument)
        }
    }
}

@Composable
fun HighlightedText(
    fullText: String,
    query: String,
    highlightColor: Color = Color.Red,
    textComp: @Composable (AnnotatedString) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        val startIndex = fullText.indexOf(query, ignoreCase = true)
        if (startIndex >= 0) {
            append(fullText.substring(0, startIndex))  // Before match
            withStyle(style = SpanStyle(color = highlightColor)) {
                append(fullText.substring(startIndex, startIndex + query.length))  // Matched part
            }
            append(fullText.substring(startIndex + query.length))  // After match
        } else {
            append(fullText)  // No match, append full text
        }
    }
    textComp(annotatedString)
}