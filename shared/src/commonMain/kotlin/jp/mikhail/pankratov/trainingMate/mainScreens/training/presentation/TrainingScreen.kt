package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.LineChartSample
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
            modifier = Modifier.fillMaxSize().padding(horizontal = Dimens.Padding16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding16.dp)
        ) {
            TextLarge(text = state.greeting)

            if (state.lastTrainings?.isNotEmpty() == true) {
                TextLarge(text = "Last training:".uppercase())
                val lastTraining = state.lastTrainings.last()
                TrainingItem(training = lastTraining, onClick = {
                    navigator.navigate(route = "${Routs.HistoryScreens.historyInfo}/${lastTraining.id}")
                })
            }

            state.availableTrainings?.let { trainings ->
                TextLarge(text = "Choose your training:".uppercase())
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(trainings) { training ->
                        LocalTrainingItem(
                            training = training,
                            onClick = {
                                if (state.ongoingTraining?.trainingTemplateId == training.id) {

                                    navigator.navigate(Routs.TrainingScreens.trainingGroupRout)
                                    return@LocalTrainingItem
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
                AnimatedVisibility(visible = lastTrainings.size >= 2) {
                    LineChartSample(
                        data = lastTrainings.map { it.totalWeightLifted },
                        xAxisData = lastTrainings.map { it.name }
                    )
                }
            }
            MaterialTheme.colorScheme.apply {
                val colorEntries = listOf(
                    ColorEntry("primary", primary),
                    ColorEntry("onPrimary", onPrimary),
                    ColorEntry("primaryContainer", primaryContainer),
                    ColorEntry("onPrimaryContainer", onPrimaryContainer),
                    ColorEntry("inversePrimary", inversePrimary),
                    ColorEntry("secondary", secondary),
                    ColorEntry("onSecondary", onSecondary),
                    ColorEntry("secondaryContainer", secondaryContainer),
                    ColorEntry("onSecondaryContainer", onSecondaryContainer),
                    ColorEntry("tertiary", tertiary),
                    ColorEntry("onTertiary", onTertiary),
                    ColorEntry("tertiaryContainer", tertiaryContainer),
                    ColorEntry("onTertiaryContainer", onTertiaryContainer),
                    ColorEntry("background", background),
                    ColorEntry("onBackground", onBackground),
                    ColorEntry("surface", surface),
                    ColorEntry("onSurface", onSurface),
                    ColorEntry("surfaceVariant", surfaceVariant),
                    ColorEntry("onSurfaceVariant", onSurfaceVariant),
                    ColorEntry("surfaceTint", surfaceTint),
                    ColorEntry("inverseSurface", inverseSurface),
                    ColorEntry("inverseOnSurface", inverseOnSurface),
                    ColorEntry("error", error),
                    ColorEntry("onError", onError),
                    ColorEntry("errorContainer", errorContainer),
                    ColorEntry("onErrorContainer", onErrorContainer),
                    ColorEntry("outline", outline),
                    ColorEntry("outlineVariant", outlineVariant),
                    ColorEntry("scrim", scrim)
                )
                LazyRow {
                    items(colorEntries) { entity ->
                        Column {
                            Text(text = entity.name + " /")
                            Box(
                                modifier = Modifier.height(50.dp).width(20.dp)
                                    .background(entity.color)
                            )
                        }
                    }
                }
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

data class ColorEntry(
    val name: String,
    val color: Color  // Assuming Color is a predefined type representing color values
)