package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> LineChart(
    data: List<T>,
    weightSelector: (T) -> Float,
    modifier: Modifier = Modifier
) {
    val maxWeight = (data.maxOfOrNull(weightSelector)?.times(1.5f)) ?: 1f

    val numberOfDivisions = 5
    val divisionValue = maxWeight / numberOfDivisions

    var sizeValue by remember { mutableStateOf(Size.Zero) }

    Row(
        modifier = modifier
            .padding(all = Dimens.Padding16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.padding(end = Dimens.Padding16.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in numberOfDivisions downTo 0) {
                val weightValue = (divisionValue * i).toInt()
                TextSmall(text = weightValue.toString())
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            sizeValue = size // Remember the current size of the canvas
            val dotRadius = 8f
            val spaceBetween = size.width / (data.size - 1)

            val points = data.mapIndexed { index, item ->
                val x = index * spaceBetween
                val y = size.height - (weightSelector(item) / maxWeight) * size.height
                Offset(x, y)
            }

            // Draw the line between points
            for (i in 0 until points.size - 1) {
                drawLine(
                    color = Color.Blue,
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 4f
                )
            }

            // Draw points on the line
            points.forEach { point ->
                drawCircle(
                    color = Color.Red,
                    radius = dotRadius,
                    center = point
                )
            }
        }
    }
}
