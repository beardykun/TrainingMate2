package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.ActionIcon
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonButton
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SwipeableItemWithActions
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TopAppBarScaffold
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation.ExerciseListItem
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables.ExerciseItem
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.cd_add_new_exercises_button
import maxrep.shared.generated.resources.finish_training
import maxrep.shared.generated.resources.no_exercises_in_training
import maxrep.shared.generated.resources.ok
import maxrep.shared.generated.resources.training_score
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ThisTrainingScreen(
    state: ThisTrainingState,
    onEvent: (ThisTrainingEvent) -> Unit,
    navigator: Navigator
) {
    TopAppBarScaffold(
        label = Routs.TrainingScreens.trainingExercises,
        onBackPressed = { navigator.navigate(Routs.MainScreens.training.title) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(
                        route = Routs.TrainingScreens.addExercises
                    )
                },
                modifier = Modifier.padding(bottom = Dimens.Padding64)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Res.string.cd_add_new_exercises_button.getString()
                )
            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TrainingComparison(
                    lastTraining = state.lastTraining,
                    ongoingTraining = state.ongoingTraining,
                    trainingTime = state.timerState.trainingTime
                ) { trainingId ->
                    navigator.navigate(route = "${Routs.HistoryScreens.historyInfo}/${trainingId}")
                }

                state.exerciseLocalsWithHeaders?.let { exercises ->
                    if (exercises.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            TextLarge(Res.string.no_exercises_in_training.getString())
                        }
                    }
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(
                            items = exercises,
                            key = { item ->
                                when (item) {
                                    is ExerciseListItem.Header -> item.muscleGroup
                                    is ExerciseListItem.ExerciseItem -> item.exercise.name
                                }
                            }) { item ->
                            when (item) {
                                is ExerciseListItem.Header -> {
                                    TextLarge(
                                        text = item.muscleGroup.uppercase(),
                                        modifier = Modifier.padding(start = Dimens.Padding16)
                                            .clip(RoundedCornerShape(25))
                                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                                            .padding(Dimens.Padding4)
                                            .animateItem()
                                    )
                                }

                                is ExerciseListItem.ExerciseItem -> {
                                    SwipeableItemWithActions(
                                        isRevealed = item.isOptionsReveled,
                                        onExpanded = {
                                            onEvent(ThisTrainingEvent.OnExtendedEvent(item))
                                        },
                                        onCollapsed = {
                                            onEvent(
                                                ThisTrainingEvent.OnCollapsedEvent(
                                                    item
                                                )
                                            )
                                        },
                                        content = {
                                            ExerciseItem(
                                                exerciseLocal = item.exercise,
                                                onClick = {
                                                    onEvent(
                                                        ThisTrainingEvent.OnExerciseClick(
                                                            it,
                                                            navigateToExercise = { exerciseId ->
                                                                navigator.navigate(
                                                                    "${Routs.ExerciseScreens.exerciseAtWork}/${state.ongoingTraining?.id}/${exerciseId}/${state.ongoingTraining?.trainingTemplateId}"
                                                                )
                                                            })
                                                    )
                                                },
                                                isDone = state.ongoingTraining?.doneExercises?.contains(
                                                    item.exercise.name
                                                ) == true,
                                                isStrengthDefining = item.exercise.isStrengthDefining,
                                                modifier = Modifier.animateItem()
                                            )
                                        },
                                        actions = {
                                            ActionIcon(
                                                onClick = {
                                                    onEvent(
                                                        ThisTrainingEvent.OnRemoveExercise(
                                                            item.exercise.name
                                                        )
                                                    )
                                                },
                                                icon = Icons.Default.Delete,
                                                backgroundColor = Color.Red,
                                                modifier = Modifier
                                            )
                                            /* ActionIcon(
                                                 onClick = {
                                                     onEvent(ThisTrainingEvent.OnCollapsedEvent(item))
                                                 },
                                                 icon = Icons.Default.Edit,
                                                 backgroundColor = Color.Blue,
                                                 modifier = Modifier
                                             )*/
                                        }
                                    )
                                }
                            }
                        }
                    }
                    CommonButton(
                        onClick = {
                            onEvent(ThisTrainingEvent.OnScoreTraining)
                        },
                        text = Res.string.finish_training.getString()
                    )
                }
            }
            AnimatedVisibility(visible = state.score != null) {
                state.score?.let { score ->
                    TrainingScorePopup(
                        score.score,
                        score.comment.getString(),
                        onDismiss = {
                            onEvent(ThisTrainingEvent.EndTraining)
                            navigator.navigate(Routs.MainScreens.training.title)
                        })
                }
            }
        })
}

@Composable
fun TrainingScorePopup(
    score: Int,
    comment: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)) // Dim background
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                // Prevent click-through
            },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(Dimens.Padding16)
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(Dimens.Padding16),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = Dimens.cardElevation
        ) {
            Column(
                modifier = Modifier.padding(Dimens.Padding24),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Res.string.training_score.getString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(Dimens.Padding16))

                Text(
                    text = "$score%",
                    style = MaterialTheme.typography.displayMedium,
                    color = if (score >= 90) Color.Green else Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Dimens.Padding16))

                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Dimens.Padding24))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.Padding8)
                ) {
                    Text(
                        text = Res.string.ok.getString(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

