package jp.mikhail.pankratov.trainingMate.exerciseAtWorkHistory.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import jp.mikhail.pankratov.trainingMate.core.listToString
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ExerciseAtWorkHistoryScreen(
    state: ExerciseAtWorkHistoryState,
    onEvent: (ExerciseAtWorkHistoryEvent) -> Unit,
    navigator: Navigator
) {

    LaunchedEffect(Unit) {
        onEvent(ExerciseAtWorkHistoryEvent.OnExerciseHistoryLoad)
    }

    state.historyList?.let { exercises ->
        LazyColumn {
            items(exercises) { exercise ->
                val sets = exercise.sets.listToString()
                if (sets.isBlank()) return@items
                Text(text = "Date ${exercise.date}")
                Text(text = sets)
            }
        }
    }
}