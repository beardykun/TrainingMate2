package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import androidx.compose.runtime.Composable
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.MyHorizontalViewPager
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TopAppBarScaffold
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun SummaryScreen(
    state: SummaryScreenState,
    onEvent: (SummaryScreenEvent) -> Unit,
    navigator: Navigator
) {
    TopAppBarScaffold(
        label = Routs.SummaryScreens.summaryScreen,
        onBackPressed = { navigator.navigate(Routs.MainScreens.training.title) },
        content = {
            state.summaryDataToDisplay?.let { summaryItem ->
                val tabs = SummaryTabs.entries.map { it.tabName.getString() }
                MyHorizontalViewPager(
                    pageNames = tabs,
                    onTabChanged = { item ->
                        onEvent(
                            SummaryScreenEvent.OnTabChanged(
                                SummaryTabs.entries[tabs.indexOf(
                                    item
                                )]
                            )
                        )
                    }
                ) {
                    when (summaryItem) {
                        is SummaryPieChatData -> PieChatContent(summaryItem)
                        is SummaryBarChatData -> BarChatContent(summaryItem)
                    }
                }
            }
        }
    )
}



