package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.TrainingItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HistoryScreen(
    state: HistoryScreenState,
    onEvent: (HistoryScreenEvent) -> Unit,
    navigator: Navigator
) {
    val listState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxSize()) {
        state.historyList?.let { trainings ->
            if (trainings.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    TextLarge(SharedRes.strings.no_history.getString())
                }
            } else {
                LazyColumn(state = listState) {
                    items(
                        items = trainings,
                        key = { training ->
                            training.id ?: -1
                        }) { training ->
                        TrainingItem(training = training, onClick = {
                            navigator.navigate(route = "${Routs.HistoryScreens.historyInfo}/${training.id}")
                        }, onDeleteClick = { trainingId ->
                            onEvent(HistoryScreenEvent.OnDeleteClick(trainingId = trainingId))
                        })
                    }
                }
                LaunchedEffect(key1 = listState, key2 = trainings.size) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .collect { lastVisibleItemIndex ->
                            if (lastVisibleItemIndex == trainings.size - 1 && !state.isLastPage) {
                                onEvent(HistoryScreenEvent.OnLoadNextPage)
                            }
                        }
                }
            }
            AnimatedVisibility(visible = state.showDeleteDialog) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.delete_training),
                    description = stringResource(SharedRes.strings.want_to_delete_training),
                    onAccept = {
                        onEvent(HistoryScreenEvent.OnDeleteConfirmClick)
                        navigator.navigate(Routs.TrainingScreens.trainingGroupRout)
                    },
                    onDenny = {
                        onEvent(HistoryScreenEvent.OnDeleteDenyClick)
                    }
                )
            }
        }
    }
}