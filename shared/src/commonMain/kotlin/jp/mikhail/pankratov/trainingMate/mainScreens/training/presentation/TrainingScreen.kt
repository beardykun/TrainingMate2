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
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.SummaryMonthly
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.SummaryWeekly
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
            navigator.navigate(Routs.TrainingScreens.trainingGroupRout)
        }) {
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = stringResource(SharedRes.strings.cd_add_new_training)
            )
        }
    }) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = Dimens.Padding16)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding16)
        ) {
            TextLarge(text = state.greeting)

            state.lastTraining?.let { lastTraining ->
                TextLarge(text = stringResource(SharedRes.strings.last_training).uppercase())
                TrainingItem(
                    training = lastTraining,
                    onClick = {
                        navigator.navigate(route = "${Routs.HistoryScreens.historyInfo}/${lastTraining.id}")
                    },
                    onDeleteClick = {
                        onEvent(TrainingScreenEvent.OnLastTrainingDelete(lastTraining.id!!))
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            state.ongoingTraining?.let { ongoingTraining ->
                TextLarge(text = stringResource(SharedRes.strings.current_training).uppercase())
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    LocalTrainingItem(
                        training = TrainingLocal(
                            id = ongoingTraining.id,
                            name = ongoingTraining.name,
                            groups = ongoingTraining.groups
                        ),
                        onClick = {
                            navigator.navigate(Routs.TrainingScreens.trainingExercises)
                        },
                        onDeleteClick = {},
                        isDeletable = false,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            state.weeklySummary?.let { weeklyList ->
                TextLarge(text = stringResource(SharedRes.strings.summaries).uppercase())
                LazyRow {
                    items(weeklyList.size) { counter ->
                        SummaryWeekly(
                            weeklySummary = weeklyList[counter],
                            counter = counter,
                            modifier = Modifier.padding(Dimens.Padding16),
                            onClick = { year, weekNum ->
                                navigator.navigate(
                                    //"${Routs.MainScreens.history.title}/${year}/${null}/${weekNum}"
                                    Routs.SummaryScreens.summaryScreensRoot
                                )
                            }
                        )
                    }
                }
            }

            state.monthlySummary?.let { monthlyList ->
                LazyRow {
                    items(monthlyList.size) { counter ->
                        SummaryMonthly(
                            monthlySummary = monthlyList[counter],
                            counter = counter,
                            modifier = Modifier.padding(Dimens.Padding16),
                            onClick = { year, monthNum ->
                                navigator.navigate(
                                    Routs.SummaryScreens.summaryScreensRoot
                                )
                                /*navigator.navigate(
                                    "${Routs.MainScreens.history.title}/${year}/${monthNum}/${null}"
                                )*/
                            }
                        )
                    }
                }
            }

            /*MaterialTheme.colorScheme.apply {
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
                            TextMedium(text = entity.name + " /")
                            Box(
                                modifier = Modifier.height(50.dp).width(20.dp)
                                    .background(entity.color)
                            )
                        }
                    }
                }
            }*/

            AnimatedVisibility(visible = state.showDeleteDialog) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.delete_last_training),
                    description = stringResource(SharedRes.strings.want_to_delete_last_training),
                    onAccept = {
                        onEvent(TrainingScreenEvent.OnDeleteConfirmClick)
                    },
                    onDenny = {
                        onEvent(TrainingScreenEvent.OnDeleteDenyClick)
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