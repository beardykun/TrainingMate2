package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import Dimens
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.ExerciseListItem
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseItem
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThisTrainingScreen(
    state: ThisTrainingState, onEvent: (ThisTrainingEvent) -> Unit, navigator: Navigator
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navigator.navigate(Routs.TrainingScreens.addExercises)
            },
            modifier = Modifier.padding(bottom = Dimens.Padding64)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(SharedRes.strings.cd_add_new_exercises_button)
            )
        }
    }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            TrainingComparison(state)

            state.exerciseLocals?.let { exercises ->
                if (exercises.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextLarge(stringResource(SharedRes.strings.no_exercises_in_training))
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
                                )
                            }

                            is ExerciseListItem.ExerciseItem -> {
                                ExerciseItem(exerciseLocal = item.exercise, onClick = {
                                    onEvent(
                                        ThisTrainingEvent.OnExerciseClick(
                                            it,
                                            navigateToExercise = { exerciseId ->
                                                navigator.navigate(
                                                    "${Routs.ExerciseScreens.exerciseAtWork}/${state.ongoingTraining?.id}/${exerciseId}"
                                                )
                                            })
                                    )
                                }, modifier = Modifier.animateItemPlacement())
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        onEvent(ThisTrainingEvent.EndTraining)
                        navigator.goBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextLarge(
                        text = stringResource(SharedRes.strings.finish_training),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TrainingComparison(state: ThisTrainingState) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = Dimens.Padding16, start = Dimens.Padding16, end = Dimens.Padding16)
            .clip(RoundedCornerShape(percent = 30))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(vertical = Dimens.Padding8)

    ) {
        Column(modifier = Modifier.weight(1f)) {
            state.lastTraining?.let { lastTraining ->
                TextLarge(
                    stringResource(
                        SharedRes.strings.last_training_lifted_weight,
                        lastTraining.totalWeightLifted,
                        Utils.countTrainingTime(lastTraining)
                    ),
                    modifier = Modifier.padding(horizontal = Dimens.Padding16)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            state.ongoingTraining?.let { ongoingTraining ->
                TextLarge(
                    stringResource(
                        SharedRes.strings.this_training_lifted_weight,
                        ongoingTraining.totalWeightLifted,
                        state.trainingTime
                    ),
                    modifier = Modifier.padding(horizontal = Dimens.Padding16)
                )
            }
        }
    }
}