package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType

@Composable
fun CommonLineChart(
    label: String,
    data: List<Double>,
    xAxisData: List<String>
) {

    val lineParameters = LineParameters(
        label = label,
        data = data,
        lineColor = Color.Gray,
        lineType = LineType.CURVED_LINE,
        lineShadow = true,
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier,
            linesParameters = listOf(lineParameters),
            isGrid = true,
            gridColor = Color.Blue,
            xAxisData = xAxisData,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 14,
            oneLineChart = false,
            gridOrientation = GridOrientation.VERTICAL,
            showXAxis = false
        )
    }
}