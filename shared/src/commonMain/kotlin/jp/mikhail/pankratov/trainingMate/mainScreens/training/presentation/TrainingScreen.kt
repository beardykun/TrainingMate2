package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.DatabaseContract
import jp.mikhail.pankratov.trainingMate.core.domain.ToastManager
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.GlobalToastMessage
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.LocalTrainingItem
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.SummaryWeekly
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.TrainingItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun TrainingScreen(
    state: TrainingScreenState,
    onEvent: (TrainingScreenEvent) -> Unit,
    navigator: Navigator
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Scaffold(floatingActionButton =
    {
        FloatingActionButton(
            onClick = {
                navigator.navigate(Routs.TrainingScreens.trainingGroupRout)
            },
            modifier = Modifier.then(
                if (state.ongoingTraining == null) {
                    Modifier.scale(scale)
                } else Modifier
            )
        ) {
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = stringResource(SharedRes.strings.cd_add_new_training)
            )
        }
    }) {
        val toastMessage = stringResource(SharedRes.strings.dummy_data_toast)

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.Padding16)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding16)
        ) {
            TextLarge(text = state.greeting)

            state.lastTraining?.let { lastTraining ->
                TextLarge(
                    text = SharedRes.strings.last_training.getString().uppercase(),
                    color = MaterialTheme.colorScheme.error
                )
                TrainingItem(
                    training = lastTraining,
                    onClick = {
                        navigator.navigate(route = "${Routs.HistoryScreens.historyInfo}/${lastTraining.id}")
                    },
                    onDeleteClick = {
                        lastTraining.id?.let { trainingId ->
                            onEvent(TrainingScreenEvent.OnLastTrainingDelete(trainingId))
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            } ?: run {
                TextLarge(
                    text = SharedRes.strings.last_training_dummy.getString().uppercase(),
                    color = MaterialTheme.colorScheme.error
                )
                TrainingItem(
                    training = Training(
                        name = SharedRes.strings.your_last_training_dummy.getString(),
                        totalLiftedWeight = 10000.0,
                        exercises = listOf(
                            "Exercise 1",
                            "Exercise 2",
                            "Exercise 3"
                        ),
                        groups = DatabaseContract.LEGS_GROUP,
                        userId = "",
                    ),
                    onClick = {
                        ToastManager.showToast(toastMessage)
                    },
                    onDeleteClick = {
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            state.ongoingTraining?.let { ongoingTraining ->
                TextLarge(
                    text = stringResource(SharedRes.strings.current_training).uppercase(),
                    color = MaterialTheme.colorScheme.error
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    LocalTrainingItem(
                        training = TrainingLocal(
                            id = ongoingTraining.id,
                            name = ongoingTraining.name,
                            groups = ongoingTraining.groups
                        ),
                        onClick = {
                            navigator.navigate(Routs.TrainingScreens.trainingExercises)
                        },
                        onDeleteClick = {},
                        isDeletable = false,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            state.weeklySummary?.let { weeklyList ->
                val emptySummary = weeklyList.isEmpty() || weeklyList.first()?.numWorkouts == 0
                val summaryTitle =
                    stringResource(if (emptySummary) SharedRes.strings.summaries_sample else SharedRes.strings.summaries)
                val summaryItem = if (emptySummary) WeeklySummary(
                    numWorkouts = 4,
                    trainingDuration = 200,
                    totalLiftedWeight = 30000.00,
                    numExercises = 10,
                    numSets = 40,
                    numReps = 400,
                    avgDurationPerWorkout = 50.00,
                    avgLiftedWeightPerExercise = 3000.00,
                    avgLiftedWeightPerWorkout = 7500.00,
                    year = 2023,
                    weekNumber = 1
                ) else weeklyList.first()
                TextLarge(text = summaryTitle.uppercase(), color = MaterialTheme.colorScheme.error)
                SummaryWeekly(
                    weeklySummary = summaryItem,
                    modifier = Modifier.padding(Dimens.Padding16),
                    onClick = { year, weekNum ->
                        if (emptySummary.not()) {
                            //"${Routs.MainScreens.history.title}/${year}/${null}/${weekNum}"
                            navigator.navigate(Routs.SummaryScreens.summaryScreensRoot)
                        } else {
                            ToastManager.showToast(toastMessage)
                        }
                    }
                )
            }

            AnimatedVisibility(visible = state.showDeleteDialog) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.delete_last_training),
                    description = stringResource(SharedRes.strings.want_to_delete_last_training),
                    onAccept = {
                        onEvent(TrainingScreenEvent.OnDeleteConfirmClick)
                    },
                    onDenny = {
                        onEvent(TrainingScreenEvent.OnDeleteDenyClick)
                    }
                )
            }
        }
    }
    GlobalToastMessage()
}