package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.TrainingItem
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.delete_training
import maxrep.shared.generated.resources.no_history
import maxrep.shared.generated.resources.want_to_delete_training
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HistoryScreen(
    state: HistoryScreenState,
    onEvent: (HistoryScreenEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(
        topBar = {
            TopAppBar(state = state, setSearchText = { newText ->
                onEvent(HistoryScreenEvent.OnSearchTextChange(newText = newText))
            }, onSearchIconClick = {
                onEvent(HistoryScreenEvent.OnSearchIconClick)
            })
        }
    ) {
        val listState = rememberLazyListState()
        Column(modifier = Modifier.fillMaxSize()) {
            state.historyList?.let { trainings ->
                if (trainings.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextLarge(Res.string.no_history.getString())
                    }
                } else {
                    LazyColumn(state = listState) {
                        items(
                            items = trainings,
                            key = { training ->
                                training.id ?: -1
                            }) { training ->
                            TrainingItem(training = training, query = state.searchText, onClick = {
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
                        title = Res.string.delete_training.getString(),
                        description = Res.string.want_to_delete_training.getString(),
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    state: HistoryScreenState,
    setSearchText: (String) -> Unit,
    onSearchIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            AnimatedVisibility(state.isExpanded) {
                TextField(
                    value = state.searchText,
                    onValueChange = { setSearchText(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        TextMedium(
                            "stringResource(Res.string.hint_search)",
                            color = Color.White
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text).copy(
                        imeAction = ImeAction.Done
                    )
                )
            }
            AnimatedVisibility(state.isExpanded.not()) {
                Text(
                    text = Routs.MainScreens.history.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        },
        actions = {
            IconButton(onClick = { onSearchIconClick.invoke() }) {
                Icon(
                    imageVector = if (state.isExpanded) Icons.Filled.Close else Icons.Filled.Search,
                    contentDescription = "stringResource(Res.string.hint_search)"
                )
            }
        }
    )
}