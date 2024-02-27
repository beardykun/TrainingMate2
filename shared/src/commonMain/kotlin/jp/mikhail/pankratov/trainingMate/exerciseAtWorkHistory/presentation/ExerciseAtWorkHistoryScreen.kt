package jp.mikhail.pankratov.trainingMate.exerciseAtWorkHistory.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
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
            items(
                items = exercises,
                key = { item ->
                    item.id ?: -1
                }) { exercise ->
                val sets = exercise.sets.listToString()
                if (sets.isBlank()) return@items
                TextMedium(text = stringResource(SharedRes.strings.date, exercise.date))
                TextMedium(text = sets)
            }
        }
    }
}