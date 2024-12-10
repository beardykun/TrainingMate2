package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation

import Dimens
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.MyHorizontalViewPager
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TopAppBarScaffold
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.ExerciseHistoryItem
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.date
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExerciseAtWorkHistoryScreen(
    state: ExerciseAtWorkHistoryState,
    onEvent: (ExerciseAtWorkHistoryEvent) -> Unit,
    navigator: Navigator
) {
    TopAppBarScaffold(label = Routs.ExerciseScreens.exerciseAtWorkHistory,
        onBackPressed = { navigator.goBack() }, content = {
            state.historyExercisesToDisplay?.let { exercises ->
                val tabs = ExerciseAtWorkHistoryTabs.entries.map { it.title.getString() }
                MyHorizontalViewPager(
                    pageNames = tabs,
                    onTabChanged = {
                        onEvent(
                            ExerciseAtWorkHistoryEvent.OnTabChanged(
                                ExerciseAtWorkHistoryTabs.entries[tabs.indexOf(
                                    it
                                )]
                            )
                        )
                    }) {
                    LazyColumn {
                        items(
                            items = exercises,
                            key = { item ->
                                item.id ?: -1
                            }) { exercise ->
                            if (exercise.totalLiftedWeight == 0.0) return@items
                            TextMedium(
                                text = stringResource(Res.string.date, exercise.date),
                                modifier = Modifier.padding(
                                    start = Dimens.Padding16,
                                    top = Dimens.Padding8
                                )
                            )
                            ExerciseHistoryItem(
                                exercise = exercise,
                                modifier = Modifier.fillParentMaxWidth()
                            )
                        }
                    }
                }
            }
        })
}