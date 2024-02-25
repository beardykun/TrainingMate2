package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HistoryInfoScreen(
    state: HistoryInfoState,
    onEvent: (HistoryInfoEvent) -> Unit,
    navigator: Navigator
) {
    Column(modifier = Modifier.fillMaxSize().padding(all = Dimens.Padding16)) {
        state.training?.let { training ->
            TextLarge(text = "Training name: ${training.name}")
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(text = "Training groups: ${training.groups}")
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(text = "Training duration: ${Utils.countTrainingTime(training)}")
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(text = "Total Lifted weight: ${training.totalWeightLifted}")
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                navigator.popBackStack()
            }, modifier = Modifier.weight(1f)) {
                TextMedium(text = "Back")
            }
            Button(onClick = {
                onEvent(HistoryInfoEvent.OnContinueTraining {
                    navigator.popBackStack()
                })
            }, modifier = Modifier.weight(1f)) {
                TextMedium(text = "Continue training")
            }
        }
        state.exercises?.let { exercises ->
            LazyColumn {
                items(exercises) { exercise ->
                    Card(
                        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
                        modifier = Modifier.padding(all = Dimens.Padding8).fillParentMaxWidth()
                    ) {

                        Column(modifier = Modifier.padding(all = Dimens.Padding16)) {
                            TextMedium(text = exercise?.name.toString())
                            TextMedium(text = "Lifted weight: ${exercise?.totalLiftedWeight.toString()}")
                            Spacer(modifier = Modifier.height(Dimens.Padding8))
                            TextMedium(text = "Sets: ")
                            exercise?.sets?.let { sets ->
                                sets.forEach { set ->
                                    TextMedium(text = set)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}