package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.addExercises.presentation.ExerciseListItem
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.exercise.presentation.ExerciseItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ThisTrainingScreen(
    state: ThisTrainingState, onEvent: (ThisTrainingEvent) -> Unit, navigator: Navigator
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navigator.navigate(Routs.TrainingScreens.addExercises)
            },
            modifier = Modifier.padding(bottom = Dimens.Padding64.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add new exercises button")
        }
    }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            TextLarge(
                state.trainingTime,
                modifier = Modifier.padding(horizontal = Dimens.Padding16.dp)
            )
            state.exerciseLocals?.let { exercises ->
                if (exercises.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextLarge("No exercises in this training yet")
                    }
                }
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(exercises) { item ->
                        when (item) {
                            is ExerciseListItem.Header -> {
                                TextLarge(
                                    text = item.muscleGroup.uppercase(),
                                    modifier = Modifier.padding(start = Dimens.Padding16.dp)
                                        .clip(RoundedCornerShape(25))
                                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                                        .padding(Dimens.Padding4.dp)
                                )
                            }

                            is ExerciseListItem.ExerciseItem -> {
                                ExerciseItem(exerciseLocal = item.exercise) {
                                    onEvent(
                                        ThisTrainingEvent.OnExerciseClick(
                                            it,
                                            navigateToExercise = { exerciseId ->
                                                navigator.navigate(
                                                    "${Routs.ExerciseScreens.exerciseAtWork}/${state.ongoingTraining?.id}/${exerciseId}"
                                                )
                                            })
                                    )
                                }
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
                        text = "End Training",
                        color = Color.White
                    )
                }
            }
        }
    }
}