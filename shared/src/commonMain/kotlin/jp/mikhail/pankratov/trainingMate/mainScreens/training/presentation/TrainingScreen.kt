package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun TrainingScreen(
    state: TrainingScreenState,
    onEvent: (TrainingScreenEvent) -> Unit,
    navigator: Navigator
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextLarge(text = state.greeting)
        TextMedium(text = "Previous workout time and lifted weight")
        TextMedium(text = "Maybe a mini progress bar to next achievement?")

        state.availableTrainings?.let { trainings ->
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(trainings) { training ->
                    TrainingItem(
                        name = training.name,
                        group = training.groups,
                        description = training.description
                    ) {
                        navigator.navigate(Routs.TrainingScreens.trainingGroupRout + "/${training.id}")
                    }
                }
            }
        }
    }
}