package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> BarChart(
    data: List<T>,
    weightSelector: (T) -> Float,
    dateSelector: (T) -> String,
    modifier: Modifier = Modifier
) {
    val bars = data.map(weightSelector)
    val dates = data.map(dateSelector)

    Box(
        modifier = modifier.padding(start = Dimens.Padding32.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barWidth = size.width / (2 * bars.size - 1)
            bars.forEachIndexed { index, bar ->
                val barHeight = bar * size.height
                drawRect(
                    color = Color.Blue,
                    size = Size(barWidth, barHeight),
                    topLeft = Offset(index * 2 * barWidth, size.height - barHeight)
                )
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(bottom = Dimens.Padding8.dp)
        ) {
            bars.forEach { weight ->
                TextMedium(
                    text = weight.toInt().toString(),
                    modifier = Modifier.padding(bottom = Dimens.Padding8.dp)
                )
            }
        }

        Row(
            modifier = Modifier.align(Alignment.TopStart).padding(top = Dimens.Padding8.dp),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Padding8.dp)
        ) {
            dates.forEach { date ->
                TextMedium(text = date)
            }
        }
    }
}
