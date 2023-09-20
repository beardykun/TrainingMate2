package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.exercise.presentation.ExerciseItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun AddExercisesScreen(state: AddExercisesState,navigator: Navigator) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        state.availableExercises?.let { exercises ->
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