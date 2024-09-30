package jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableExercises
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddExercisesScreen(
    state: AddExercisesState,
    onEvent: (AddExercisesEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navigator.navigate(Routs.TrainingScreens.createExercise)
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(SharedRes.strings.create_new_ex_btn)
            )
        }
    }) { padding ->
        if(!state.availableExerciseLocals.isNullOrEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = Dimens.Padding16, vertical = Dimens.Padding8)
            ) {
                val exercisesTypes = listOf(SelectionType.ADD, SelectionType.REMOVE)
                val pagerState = rememberPagerState(pageCount = { exercisesTypes.size })
                var selectedTabIndex by remember { mutableStateOf(0) }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(pagerState.currentPage) {
                    val selectedType = exercisesTypes[pagerState.currentPage]
                    selectedTabIndex = pagerState.currentPage
                    onEvent(AddExercisesEvent.OnSelectionChanged(selectedType))
                }
                state.sortedExercises?.let { exercises ->
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        exercisesTypes.forEachIndexed { index, trainingType ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }) {
                                TextMedium(
                                    text = trainingType.name,
                                    modifier = Modifier.padding(Dimens.Padding8)
                                )
                            }
                        }
                    }
                    HorizontalPager(state = pagerState) {
                        Column {
                            SelectableExercises(
                                exerciseLocals = exercises,
                                isSelected = state.selectedExercises,
                                modifier = Modifier.weight(1f),
                                onClick = { exercise ->
                                    onEvent(AddExercisesEvent.OnSelectExercise(exercise.name))
                                },
                                onDeleteClick = { exercise ->
                                    onEvent(
                                        AddExercisesEvent.OnDisplayDeleteDialog(
                                            exercise = exercise,
                                            isDeleteVisible = true
                                        )
                                    )
                                }
                            )

                            Button(
                                onClick = {
                                    onEvent(AddExercisesEvent.OnAddNewExercises {
                                        navigator.navigate(Routs.TrainingScreens.trainingExercises)
                                    })
                                }, modifier = Modifier.fillMaxWidth()
                            ) {
                                TextMedium(text = stringResource(SharedRes.strings.apply_changes))
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = state.isDeleteDialogVisible) {
                    DialogPopup(
                        title = stringResource(SharedRes.strings.delete_set),
                        description = stringResource(SharedRes.strings.sure_delete_set),
                        onAccept = {
                            onEvent(AddExercisesEvent.OnDeleteExercise)
                        },
                        onDenny = {
                            onEvent(AddExercisesEvent.OnDisplayDeleteDialog(false))
                        }
                    )
                }
            }
        }
    }
}