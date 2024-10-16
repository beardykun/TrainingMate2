package jp.mikhail.pankratov.trainingMate.trainigSelection.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import jp.mikhail.pankratov.trainingMate.core.domain.Constants
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.LocalTrainingItem
import kotlinx.coroutines.launch
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.abs
import maxrep.shared.generated.resources.are_you_ready_to_start
import maxrep.shared.generated.resources.back
import maxrep.shared.generated.resources.biceps
import maxrep.shared.generated.resources.cd_add_new_training
import maxrep.shared.generated.resources.chest
import maxrep.shared.generated.resources.delete_training
import maxrep.shared.generated.resources.legs
import maxrep.shared.generated.resources.shoulders
import maxrep.shared.generated.resources.start_training
import maxrep.shared.generated.resources.triceps
import maxrep.shared.generated.resources.want_to_delete_training
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource

@Composable
fun TrainingSelectionScreen(
    state: TrainingSelectionState,
    onEvent: (TrainingSelectionEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(floatingActionButton =
    {
        FloatingActionButton(
            onClick = {
                navigator.navigate(Routs.TrainingScreens.createTraining)
            },
            modifier = Modifier.padding(bottom = Dimens.Padding64)
        ) {
            Icon(
                imageVector = Icons.Default.AddCard,
                contentDescription = Res.string.cd_add_new_training.getString()
            )
        }
    }) {
        state.availableTrainings?.let {
            Column {
                val trainingTypes = Constants.GROUPS
                val trainingImages = listOf(
                    Res.drawable.biceps,
                    Res.drawable.triceps,
                    Res.drawable.shoulders,
                    Res.drawable.back,
                    Res.drawable.chest,
                    Res.drawable.legs,
                    Res.drawable.abs
                )
                val pagerState = rememberPagerState(pageCount = { trainingTypes.size })
                var selectedTabIndex by remember { mutableStateOf(0) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(pagerState.currentPage) {
                    val trainingType = trainingTypes[pagerState.currentPage]
                    selectedTabIndex = pagerState.currentPage
                    onEvent(TrainingSelectionEvent.OnTrainingTypeChanged(trainingType))
                }

                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = Dimens.Padding16
                ) {
                    trainingTypes.forEachIndexed { index, trainingType ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                selectedTabIndex = index
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(trainingImages[index]),
                                    contentDescription = trainingType
                                )
                                TextMedium(
                                    text = trainingType,
                                    modifier = Modifier.padding(Dimens.Padding8)
                                )
                            }
                        }
                    }
                }
                val nestedScrollConnection = remember { object : NestedScrollConnection {} }
                state.typedTrainings?.let { trainings ->
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .nestedScroll(nestedScrollConnection)
                    ) { page ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(trainings, key = { it.name }) { training ->
                                LocalTrainingItem(
                                    training = training,
                                    onClick = {
                                        onEvent(
                                            TrainingSelectionEvent.OnTrainingItemClick(
                                                shouldShowDialog = true,
                                                training = training
                                            )
                                        )
                                    },
                                    onDeleteClick = { id ->
                                        onEvent(TrainingSelectionEvent.OnTrainingTemplateDelete(id))
                                    },
                                    modifier = Modifier.animateItem(),
                                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = state.showDeleteTemplateDialog) {
                    DialogPopup(
                        title = Res.string.delete_training.getString(),
                        description = Res.string.want_to_delete_training.getString(),
                        onAccept = {
                            onEvent(TrainingSelectionEvent.OnDeleteTemplateConfirmClick)
                        },
                        onDenny = {
                            onEvent(TrainingSelectionEvent.OnDeleteTemplateDenyClick)
                        }
                    )
                }

                AnimatedVisibility(visible = state.showStartTrainingDialog) {
                    DialogPopup(
                        title = Res.string.start_training.getString(),
                        description = Res.string.are_you_ready_to_start.getString(),
                        onAccept = {
                            onEvent(TrainingSelectionEvent.OnStartNewTraining {
                                navigator.navigate(
                                    Routs.TrainingScreens.trainingExercises
                                )
                            })
                        },
                        onDenny = {
                            onEvent(TrainingSelectionEvent.OnStartNewTrainingDeny)
                        }
                    )
                }
            }
        }
    }
}