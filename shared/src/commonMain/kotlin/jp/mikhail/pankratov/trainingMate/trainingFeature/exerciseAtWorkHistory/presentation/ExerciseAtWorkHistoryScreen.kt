package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation

import Dimens
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.ExerciseHistoryItem
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.date
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource

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
                if (exercise.totalLiftedWeight == 0.0) return@items
                TextMedium(
                    text = stringResource(Res.string.date, exercise.date),
                    modifier = Modifier.padding(start = Dimens.Padding16, top = Dimens.Padding8)
                )
                ExerciseHistoryItem(
                    exercise = exercise,
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }
    }
}