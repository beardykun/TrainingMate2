package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.aay.compose.barChart.model.BarParameters
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonBarChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DropDown
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import kotlinx.coroutines.launch
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.another_exercise
import maxrep.shared.generated.resources.another_training
import maxrep.shared.generated.resources.no_analysis_data

@Composable
fun TabsComposable(
    chartLabel: String,
    categories: List<MetricsMode>,
    metricsMode: MetricsMode,
    metricsData: List<Double>?,
    metricsXAxisData: List<String>?,
    analysisMode: String,
    isDropdownExpanded: Boolean,
    onEvent: (AnalysisScreenEvent) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { categories.size })
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        val category = categories[pagerState.currentPage]
        selectedTabIndex = pagerState.currentPage
        onEvent(AnalysisScreenEvent.OnMetricsModeChanged(category))
    }
    if (metricsXAxisData.isNullOrEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TextLarge(Res.string.no_analysis_data.getString())
        }
    } else {
        TabRow(selectedTabIndex = selectedTabIndex) {
            categories.forEachIndexed { index, category ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                    TextMedium(
                        text = category.name,
                        modifier = Modifier.padding(Dimens.Padding8)
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(Dimens.tabHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(metricsMode != MetricsMode.GENERAL && !metricsData.isNullOrEmpty()) {
                Button(
                    onClick = {
                        onEvent(AnalysisScreenEvent.OnMetricsModeChanged(metricsMode))
                    }, modifier = Modifier.padding(horizontal = Dimens.Padding16)
                ) {
                    val text =
                        if (metricsMode == MetricsMode.EXERCISE)
                            Res.string.another_exercise.getString()
                        else
                            Res.string.another_training.getString()
                    TextMedium(text = text)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(metricsMode != MetricsMode.EXERCISE && !metricsData.isNullOrEmpty()) {
                DropDown(
                    initValue = analysisMode,
                    isOpen = isDropdownExpanded,
                    onClick = { onEvent(AnalysisScreenEvent.OnDropdownOpen) },
                    onDismiss = { onEvent(AnalysisScreenEvent.OnDropdownClosed) },
                    onSelectedValue = { value ->
                        onEvent(AnalysisScreenEvent.OnAnalysisModeChanged(value))
                    },
                    values = AnalysisMode.entries.map { it.name },
                    modifier = Modifier.clip(
                        RoundedCornerShape(percent = 50)
                    ).background(color = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }

        HorizontalPager(state = pagerState) {
            AnimatedVisibility(visible = !metricsData.isNullOrEmpty()) {
                metricsData?.let { data ->
                    CommonBarChart(
                        params = listOf(
                            BarParameters(
                                dataName = chartLabel,
                                data = data,
                                barColor = MaterialTheme.colorScheme.primary
                            )
                        ),
                        xAxisData = metricsXAxisData
                    )
                }
            }
        }
    }
}