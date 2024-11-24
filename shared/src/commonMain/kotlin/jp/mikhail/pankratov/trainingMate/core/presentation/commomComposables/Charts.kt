package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.aay.compose.radarChart.RadarChart
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon
import com.aay.compose.radarChart.model.PolygonStyle

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
            oneLineChart = true,
            gridOrientation = GridOrientation.VERTICAL,
            showXAxis = false
        )
    }
}

@Composable
fun CommonBarChart(
    params: List<BarParameters>,
    xAxisData: List<String> = listOf(""),
    yAxisRange: Int = 20,
    spaceBetweenBars: Dp = Dimens.Padding32,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        BarChart(
            chartParameters = params,
            gridColor = Color.DarkGray,
            xAxisData = xAxisData,
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 12.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.W400
            ),
            showXAxis = false,
            yAxisRange = yAxisRange,
            barWidth = 20.dp,
            spaceBetweenBars = spaceBetweenBars
        )
    }
}

@Composable
fun CommonPieChart(
    list: List<PieChartData>,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        PieChart(
            modifier = Modifier.fillMaxSize(),
            animation = TweenSpec(durationMillis = 1000),
            pieChartData = list,
            ratioLineColor = Color.LightGray,
            textRatioStyle = TextStyle(color = Color.Gray),
            legendPosition = LegendPosition.BOTTOM
        )
    }
}

@Composable
fun CommonRadarChart(map: Map<String, Int>) {
    val labels = map.map { "${it.key}: ${it.value}%" }
    val values = map.values.map { it.toDouble() }
    val labelsStyle = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )

    val scalarValuesStyle = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
    val chartMax = maxOf(50.0, roundUpToNearest50(values.max()))
    RadarChart(
        modifier = Modifier.fillMaxSize(),
        radarLabels = labels,
        labelsStyle = labelsStyle,
        netLinesStyle = NetLinesStyle(
            netLineColor = Color(0x90ffD3CFD3),
            netLinesStrokeWidth = 2f,
            netLinesStrokeCap = StrokeCap.Butt
        ),
        scalarSteps = (chartMax / 50).toInt() + 1,
        scalarValue = chartMax,
        scalarValuesStyle = scalarValuesStyle,
        polygons = listOf(
            Polygon(
                values = values,
                unit = "%",
                style = PolygonStyle(
                    fillColor = Color(0xffFFDBDE),
                    fillColorAlpha = 0.5f,
                    borderColor = Color(0xffFF8B99),
                    borderColorAlpha = 0.5f,
                    borderStrokeWidth = 2f,
                    borderStrokeCap = StrokeCap.Butt
                )
            )
        )
    )
}

private fun roundUpToNearest50(value: Double): Double {
    return (((value + 49) / 50).toInt() * 50).toDouble()
}