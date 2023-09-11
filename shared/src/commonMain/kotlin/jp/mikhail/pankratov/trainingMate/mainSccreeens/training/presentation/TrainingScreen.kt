package jp.mikhail.pankratov.trainingMate.mainSccreeens.training.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun TrainingScreen(
    state: TrainingScreenState,
    onEvent: (TrainingScreenEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                content = {

                })
        }) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.padding(paddingValues).verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            TextLarge(text = state.greeting)

            TextMedium(text = "previous workout time and lifted weight")
            TextMedium(text = "Maybe a mini progress bar to next achievement?")

            state.availableTrainingHistories?.let { trainings ->
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(trainings) { training ->
                        TrainingItem(name = training.name, group = training.groups, image = "")
                    }
                }
            }
        }
    }
}