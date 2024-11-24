package jp.mikhail.pankratov.trainingMate.summaryFeature.domain

import androidx.compose.runtime.Composable
import com.aay.compose.barChart.model.BarParameters

@Composable
fun LocalBarParameters.toBarParameters(): BarParameters {
    return BarParameters(
        dataName = this.dataName.asString(),
        data = this.data,
        barColor = this.barColor
    )
}