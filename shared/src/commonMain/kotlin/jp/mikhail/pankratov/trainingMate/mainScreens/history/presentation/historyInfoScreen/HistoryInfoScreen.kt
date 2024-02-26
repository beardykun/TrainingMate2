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
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
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
            TextLarge(
                text = stringResource(
                    SharedRes.strings.training_name_with_arg,
                    training.name
                )
            )
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(
                    SharedRes.strings.training_groups_with_arg, training.groups
                )
            )
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(
                    SharedRes.strings.training_duration_with_arg, Utils.countTrainingTime(training)
                )
            )
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextLarge(
                text = stringResource(
                    SharedRes.strings.total_weight_lifted_with_arg, training.totalWeightLifted
                )
            )
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                navigator.popBackStack()
            }, modifier = Modifier.weight(1f)) {
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.back_btn
                    )
                )
            }
            Button(onClick = {
                onEvent(HistoryInfoEvent.OnContinueTraining {
                    navigator.popBackStack()
                })
            }, modifier = Modifier.weight(1f)) {
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.continue_training
                    )
                )
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
                            TextMedium(
                                text = stringResource(
                                    SharedRes.strings.lifted_weight_with_arg,
                                    exercise?.totalLiftedWeight ?: 0.0
                                )
                            )
                            Spacer(modifier = Modifier.height(Dimens.Padding8))
                            TextMedium(
                                text = stringResource(
                                    SharedRes.strings.sets
                                )
                            )
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