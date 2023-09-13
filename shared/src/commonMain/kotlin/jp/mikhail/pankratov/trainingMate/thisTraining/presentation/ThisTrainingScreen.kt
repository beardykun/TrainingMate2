package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.exercise.presentation.ExerciseItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ThisTrainingScreen(
    state: ThisTrainingState,
    onEvent: (ThisTrainingEvent) -> Unit,
    navigator: Navigator
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        state.exercises?.let { exercises ->
            LazyColumn {
                items(exercises) { exercise ->
                    ExerciseItem(
                        name = exercise.name,
                        group = exercise.group,
                        image = exercise.image
                    )
                }
            }
        }
    }

}