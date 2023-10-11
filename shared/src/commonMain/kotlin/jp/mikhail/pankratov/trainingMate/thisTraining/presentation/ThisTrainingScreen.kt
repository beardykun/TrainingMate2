package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import Dimens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.exercise.presentation.ExerciseItem
import kotlinx.datetime.Clock
import moe.tlaster.precompose.navigation.Navigator
import kotlin.time.Duration.Companion.seconds

@Composable
fun ThisTrainingScreen(
    state: ThisTrainingState, onEvent: (ThisTrainingEvent) -> Unit, navigator: Navigator
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navigator.navigate(Routs.TrainingScreens.addExercises)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add new exercises button")
        }
    }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            TextLarge(state.trainingTime, modifier = Modifier.padding(horizontal = Dimens.Padding16.dp))
            state.exerciseLocals?.let { exercises ->
                if (exercises.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextLarge("No exercises in this training yet")
                    }
                }
                LazyColumn {
                    items(exercises) { exercise ->
                        ExerciseItem(exerciseLocal = exercise) {
                            onEvent(
                                ThisTrainingEvent.OnExerciseClick(
                                    it,
                                    navigateToExercise = { exerciseId ->
                                        navigator.navigate(
                                            "${Routs.ExerciseScreens.exerciseAtWork}/${state.training?.id}/${exerciseId}"
                                        )
                                    })
                            )
                        }
                    }
                }
            }
        }
    }
}