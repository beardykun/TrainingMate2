package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
            oneLineChart = false,
            gridOrientation = GridOrientation.VERTICAL,
            showXAxis = false
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

@Composable
fun CommonBarChart(map: Map<String, Int>, modifier: Modifier = Modifier) {
    val exerciseColors = mapOf(
        "barbell curls" to Color(0xFF4CAF50),   // Green
        "barbell squat" to Color(0xFFFF5722),   // Deep Orange
        "bench press" to Color(0xFF2196F3),     // Blue
        "chin ups" to Color(0xFFFFEB3B),        // Yellow
        "lying triceps press" to Color(0xFF9C27B0), // Purple
        "close grip barbell press" to Color(0xFFF44336), // Red
        "barbell shoulder press" to Color(0xFF00BCD4)   // Cyan
    )
    val labels = map.map {
        BarParameters(
            dataName = "${it.key}: ${it.value}%",
            data = listOf(it.value.toDouble()),
            barColor = exerciseColors.get(key = it.key) ?: Color.Black
        )
    }

    Box(modifier = modifier) {
        BarChart(
            chartParameters = labels,
            gridColor = Color.DarkGray,
            xAxisData = listOf(""),
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.W400
            ),
            showXAxis = false,
            yAxisRange = 15,
            barWidth = 20.dp,
            spaceBetweenBars = Dimens.Padding32
        )
    }
}

@Composable
fun CommonPieChart(
    list: List<PieChartData>,
    modifier: Modifier = Modifier
) {
    PieChart(
        modifier = modifier,
        animation = TweenSpec(durationMillis = 1000),
        pieChartData = list,
        ratioLineColor = Color.LightGray,
        textRatioStyle = TextStyle(color = Color.Gray),
        descriptionStyle = TextStyle(color = Color.Black),
        legendPosition = LegendPosition.BOTTOM
    )
}