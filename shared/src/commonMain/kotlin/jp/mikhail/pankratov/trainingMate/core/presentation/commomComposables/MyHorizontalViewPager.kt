package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> MyHorizontalViewPager(
    pageNames: List<String>,
    onSelectionChanged: (String) -> Unit,
    item: T,
    onItemClick: (item: T) -> Unit,
    pageComposable: @Composable (item: T, onItemSelected: (item: T) -> Unit) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pageNames.size })
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        val selectedType = pageNames[pagerState.currentPage]
        selectedTabIndex = pagerState.currentPage
        onSelectionChanged(selectedType)
    }
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            pageNames.forEachIndexed { index, pageName ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                    TextMedium(
                        text = pageName,
                        modifier = Modifier.padding(Dimens.Padding8)
                    )
                }
            }
        }
        HorizontalPager(state = pagerState) {
            pageComposable(item, onItemClick)
        }
    }
}