package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HistiryScreen(state: HistoryScreenState, navigator: Navigator) {
    Column(modifier = Modifier.fillMaxSize()) {
        state.historyList?.let { list ->
            if (list.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    TextLarge("You don't have a training history yet...")
                }
            } else
                LazyColumn {
                    items(list) { training ->
                        TrainingItem(training = training, onClick = {
                            navigator.navigate(route = "${Routs.HistoryScreens.historyInfo}/${training.id}")
                        })
                    }
                }
        }
    }
}