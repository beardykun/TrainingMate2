package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonBarChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.toBarParameters
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.not_enough_data

@Composable
fun BarChatContent(summaryItem: SummaryBarChatData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(visible = summaryItem.barChatParams.size == 1) {
            TextLarge(
                text = Res.string.not_enough_data.getString(),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        LazyColumn(
            modifier = Modifier.padding(all = Dimens.Padding16).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding32),
        ) {
            items(summaryItem.barChatParams, key = { it.hashCode()}) { param ->
                CommonBarChart(
                    params = param.map { it.toBarParameters() },
                    xAxisData = param.first().data.map { it.toString() },
                    yAxisRange = 10,
                    spaceBetweenBars = 10.dp,
                    modifier = Modifier.height(200.dp)
                )
                HorizontalDivider()
            }
        }
    }
}