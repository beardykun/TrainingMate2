package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableExercises
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun AddExercisesScreen(
    state: AddExercisesState,
    onEvent: (AddExercisesEvent) -> Unit,
    navigator: Navigator
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        state.availableExerciseLocals?.let { exercises ->
            SelectableExercises(
                exerciseLocals = exercises,
                isSelected = state.selectedExercises,
                modifier = Modifier.weight(1f)
            ) { exercise ->
                onEvent(AddExercisesEvent.OnSelectExercise(exercise.name))
            }
        }
        Button(
            onClick = {
                onEvent(AddExercisesEvent.OnAddNewExercises {
                    navigator.popBackStack()
                })
            }) {
            Text(text = "Add exercises")
        }
    }
}