package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HistoryInfoScreen(state: HistoryInfoState, navigator: Navigator) {
    Column(modifier = Modifier.fillMaxSize()) {
        state.exercises?.let { exercises ->
            LazyColumn {
                items(exercises) { exercise ->
                    TextMedium(exercise?.sets.toString())
                }
            }
        }
    }
}