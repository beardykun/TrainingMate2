package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HistoryInfoScreen(state: HistoryInfoState, navigator: Navigator) {
    Column(modifier = Modifier.fillMaxSize().padding(all = Dimens.Padding16.dp)) {
        state.training?.let { training ->
            TextLarge(text = "Training name: ${training.name}")
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(Dimens.Padding8.dp))
            TextLarge(text = "Training groups: ${training.groups}")
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(Dimens.Padding8.dp))
            TextLarge(text = "Training duration: ${Utils.countTrainingTime(training)}")
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(Dimens.Padding8.dp))
            TextLarge(text = "Total Lifted weight: ${training.totalWeightLifted}")
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(Dimens.Padding8.dp))
        }
        state.exercises?.let { exercises ->
            LazyColumn {
                items(exercises) { exercise ->
                    Card(elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier.padding(all = Dimens.Padding8.dp).fillParentMaxWidth()) {

                        Column(modifier = Modifier.padding(all = Dimens.Padding16.dp)) {
                            TextMedium(text = exercise?.name.toString())
                            TextMedium(text = "Lifted weight: ${exercise?.totalLiftedWeight.toString()}")
                            Spacer(modifier = Modifier.height(Dimens.Padding8.dp))
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