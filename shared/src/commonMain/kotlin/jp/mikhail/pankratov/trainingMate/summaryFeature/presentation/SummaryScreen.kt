package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import androidx.compose.runtime.Composable
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.MyHorizontalViewPager
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TopAppBarScaffold
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.month
import maxrep.shared.generated.resources.month_all
import maxrep.shared.generated.resources.week
import maxrep.shared.generated.resources.week_all
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
                MyHorizontalViewPager(
                    pageNames = listOf(
                        Res.string.week.getString(),
                        Res.string.month.getString(),
                        Res.string.week_all.getString(),
                        Res.string.month_all.getString()
                    ),
                    onTabChanged = { item ->
                        onEvent(SummaryScreenEvent.OnTabChanged(item))
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



