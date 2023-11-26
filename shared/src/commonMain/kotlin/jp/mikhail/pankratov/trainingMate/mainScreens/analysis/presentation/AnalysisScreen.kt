package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.exercise.presentation.ExerciseItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun AnalysisScreen(
    state: AnalysisScreenSate,
    onEvent: (AnalysisScreenEvent) -> Unit,
    navigator: Navigator
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (state.metricsMode == MetricsMode.EXERCISE && !state.graphDisplayed) {
            state.localExercises?.let { localExercises ->
                ExerciseNameChoice(localExercises = localExercises) { exerciseName ->
                    onEvent(AnalysisScreenEvent.OnExerciseNameSelected(exerciseName))
                }
            }
        }
            TabsComposable(
                categories = listOf(
                    MetricsMode.GENERAL,
                    MetricsMode.MUSCLE_GROUP,
                    MetricsMode.TRAINING,
                    MetricsMode.EXERCISE
                ),
                trainings = state.historyTrainings,
                exercises = state.historyExercises,
                onEvent = onEvent
            )
    }
}

@Composable
fun ExerciseNameChoice(localExercises: List<ExerciseLocal>, onItemClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextLarge(text = "Select Exercise")
        LazyColumn {
            items(localExercises) { exercise ->
                ExerciseItem(exerciseLocal = exercise, onClick = {
                    onItemClick.invoke(it.name)
                })
            }
        }
    }
}