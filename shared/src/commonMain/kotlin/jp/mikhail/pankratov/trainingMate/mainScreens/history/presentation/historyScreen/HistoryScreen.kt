package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HistoryScreen(
    state: HistoryScreenState,
    onEvent: (HistoryScreenEvent) -> Unit,
    navigator: Navigator
) {
    Column(modifier = Modifier.fillMaxSize()) {
        state.historyList?.let { list ->
            if (list.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    TextLarge(stringResource(SharedRes.strings.no_history))
                }
            } else
                LazyColumn {
                    items(items = list, key = { training ->
                        training.name
                    }) { training ->
                        TrainingItem(training = training, onClick = {
                            navigator.navigate(route = "${Routs.HistoryScreens.historyInfo}/${training.id}")
                        }, onDeleteClick = { trainingId ->
                            onEvent(HistoryScreenEvent.OnDeleteClick(trainingId = trainingId))
                        })
                    }
                }

            AnimatedVisibility(visible = state.showDeleteDialog) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.start_training),
                    description = stringResource(SharedRes.strings.are_you_ready_to_start),
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