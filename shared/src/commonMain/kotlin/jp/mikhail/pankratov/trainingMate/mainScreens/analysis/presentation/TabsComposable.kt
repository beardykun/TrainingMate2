package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonLineChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsComposable(
    categories: List<MetricsMode>,
    trainings: List<Training>?,
    exercises: List<Exercise>?,
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

    TabRow(selectedTabIndex = selectedTabIndex) {
        categories.forEachIndexed { index, category ->
            Tab(selected = selectedTabIndex == index,
                onClick = {
                    onEvent(AnalysisScreenEvent.OnMetricsModeChanged(category))
                    selectedTabIndex = index
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }) {
                TextMedium(
                    text = category.name,
                    modifier = Modifier.padding(Dimens.Padding8.dp)
                )
            }
        }
    }
    HorizontalPager(state = pagerState) {
        if (selectedTabIndex == 0) {
            AnimatedVisibility(visible = !trainings.isNullOrEmpty()) {
                trainings?.let {
                    CommonLineChart(
                        data = trainings.map { it.totalWeightLifted },
                        xAxisData = trainings.map { it.name }
                    )
                }
            }
        } else {
            AnimatedVisibility(visible = !exercises.isNullOrEmpty()) {
                exercises?.let {
                    CommonLineChart(
                        data = exercises.map { it.totalLiftedWeight },
                        xAxisData = exercises.map { it.name }
                    )
                }
            }
        }
    }
}