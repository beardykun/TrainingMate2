package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.BarChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun TrainingScreen(
    state: TrainingScreenState,
    onEvent: (TrainingScreenEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(floatingActionButton =
    {
        FloatingActionButton(onClick = {
            navigator.navigate(Routs.TrainingScreens.createTraining)
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(SharedRes.strings.cd_add_new_training)
            )
        }
    }) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding16.dp)
        ) {
            TextLarge(text = state.greeting)

            state.lastTrainings?.let { lastTrainings ->
                if (lastTrainings.isNotEmpty()) {
                    TextMedium(text = "${lastTrainings.first().name} ${lastTrainings.first().totalWeightLifted}")
                }
            } ?: TextMedium(text = "last training info")

            state.availableTrainings?.let { trainings ->
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(trainings) { training ->
                        TrainingItem(
                            training = training,
                            onClick = {
                                if (state.ongoingTraining?.id == training.id) {
                                    navigator.navigate(Routs.TrainingScreens.trainingGroupRout)
                                    return@TrainingItem
                                }
                                onEvent(
                                    TrainingScreenEvent.OnTrainingItemClick(
                                        shouldShowDialog = true,
                                        training = training
                                    )
                                )
                            }
                        )
                    }
                }
            }
            state.lastTrainings?.let { lastTrainings ->
                BarChart(
                    data = lastTrainings,
                    weightSelector = { training ->
                        training.totalWeightLifted.toFloat()
                    },
                    dateSelector = { training ->
                        Utils.formatEpochMillisToDate(training.startTime ?: 0)
                    }, modifier = Modifier.height(150.dp).clipToBounds()
                )
            }

            TextMedium(text = "Maybe a mini progress bar to next achievement?")

            AnimatedVisibility(visible = state.showStartTrainingDialog) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.start_training),
                    description = stringResource(SharedRes.strings.are_you_ready_to_start),
                    onAccept = {
                        onEvent(TrainingScreenEvent.OnStartNewTraining)
                        navigator.navigate(Routs.TrainingScreens.trainingGroupRout)
                    },
                    onDenny = {
                        onEvent(TrainingScreenEvent.OnTrainingItemClick())
                    }
                )
            }
        }
    }
}