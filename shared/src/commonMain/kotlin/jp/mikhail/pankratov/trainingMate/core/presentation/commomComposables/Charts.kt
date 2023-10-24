package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun <T> BarChart(
    data: List<T>,
    weightSelector: (T) -> Float,
    dateSelector: (T) -> String,
    modifier: Modifier = Modifier
) {
    // Calculate the max weight to scale the bar heights within the chart.
    val maxWeight = data.maxOfOrNull(weightSelector) ?: 1f

    val bars = data.map(weightSelector)
    val dates = data.map(dateSelector)
    var sizeValue by remember { mutableStateOf(Size.Zero) } // We start with an initial size of zero

    Box(
        modifier = modifier
            .padding(horizontal = Dimens.Padding32.dp, vertical = Dimens.Padding16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            sizeValue = size
            val barWidth = size.width / (2 * bars.size + 1) // space for bars plus one space in between
            bars.forEachIndexed { index, bar ->
                val normalizedBarHeight = (bar / maxWeight) * size.height
                drawRect(
                    color = Color.Blue,
                    topLeft = Offset(x = (index * 2 + 1) * barWidth, y = size.height - normalizedBarHeight),
                    size = Size(width = barWidth, height = normalizedBarHeight)
                )
            }
        }

        // Drawing the texts outside of the Canvas, positioned in relation to the bars.
        Row(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            dates.forEachIndexed { index, date ->
                // Spacer for the first bar's width before the first date.
                if (index == 0) Spacer(modifier = Modifier.width(Dimens.Padding32.dp))

                TextMedium(
                    text = date,
                    modifier = Modifier.padding(end = Dimens.Padding32.dp) // for space between dates
                )
            }
        }

        // Adding this to display the weight above or at the top of the bar, adjust as needed
        Column(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            bars.forEachIndexed { index, weight ->
                // consider adding condition or formatting for larger numbers
                TextMedium(text = weight.toString())
                // This spacer will make the next weight text appear above the previous
                Spacer(modifier = Modifier.height(((weight / maxWeight) * sizeValue.height).dp))
            }
        }
    }
}

