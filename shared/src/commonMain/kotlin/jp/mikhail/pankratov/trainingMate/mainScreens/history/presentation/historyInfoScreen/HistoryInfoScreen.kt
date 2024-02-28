package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import Dimens
import androidx.compose.foundation.ExperimentalFoundationApi
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
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
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
                onEvent(HistoryInfoEvent.OnContinueTraining(
                    onSuccess = {
                        navigator.navigate(route = Routs.TrainingScreens.trainingExercises)
                    }, onError = {
                        onEvent(HistoryInfoEvent.OnError)
                    })
                )
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
                items(
                    items = exercises,
                    key = { item ->
                        item.name
                    }) { exercise ->
                    Card(
                        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
                        modifier = Modifier
                            .padding(all = Dimens.Padding8)
                            .fillParentMaxWidth()
                            .animateItemPlacement()
                    ) {

                        Column(modifier = Modifier.padding(all = Dimens.Padding16)) {
                            TextMedium(text = exercise.name)
                            TextMedium(
                                text = stringResource(
                                    SharedRes.strings.lifted_weight_with_arg,
                                    exercise.totalLiftedWeight
                                )
                            )
                            Spacer(modifier = Modifier.height(Dimens.Padding8))
                            TextMedium(
                                text = stringResource(
                                    SharedRes.strings.sets
                                )
                            )
                            exercise.sets.forEach { set ->
                                TextMedium(text = set)
                            }
                        }
                    }
                }
            }
        }
        if (state.isError) {
            DialogPopup(
                title = "Ongoing training already exists",
                description = "Finish ongoing training and continue this one?",
                onAccept = {
                    onEvent(HistoryInfoEvent.OnFinishOngoingAndContinue {
                        navigator.navigate(route = Routs.TrainingScreens.trainingExercises)
                    })
                },
                onDenny = {
                    onEvent(HistoryInfoEvent.OnFinishDeny)
                })
        }
    }
}
