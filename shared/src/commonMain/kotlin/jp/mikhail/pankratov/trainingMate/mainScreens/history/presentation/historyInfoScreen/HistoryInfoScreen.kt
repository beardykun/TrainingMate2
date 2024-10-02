package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import Dimens
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.AnimatedTextItem
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
                    SharedRes.strings.total_weight_lifted_with_arg, training.totalLiftedWeight
                )
            )
            HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
        }
        state.exercises?.let { exercises ->
            LazyColumn {
                items(
                    items = exercises,
                    key = { item ->
                        item.name
                    }) { exercise ->
                    ExerciseHistoryItem(
                        exercise, modifier = Modifier
                            .fillParentMaxWidth()
                            .animateItemPlacement()
                    )
                }
            }
        }
        if (state.isError) {
            DialogPopup(
                title = stringResource(SharedRes.strings.training_ongoing),
                description = stringResource(SharedRes.strings.finish_ongoing_and_continue),
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

@Composable
fun ExerciseHistoryItem(exercise: Exercise, modifier: Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        modifier = modifier
            .padding(all = Dimens.Padding8)

    ) {

        Column(modifier = Modifier.padding(all = Dimens.Padding16)) {
            TextMedium(text = exercise.name.uppercase())
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextMedium(
                text = stringResource(
                    SharedRes.strings.sets
                )
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            FixedGrid(columns = 3, items = exercise.sets)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextMedium(
                text = stringResource(
                    SharedRes.strings.lifted_weight_with_arg,
                    exercise.totalLiftedWeight
                )
            )
        }
    }
}

@Composable
fun FixedGrid(
    columns: Int = 3, // Number of columns in the grid
    items: List<ExerciseSet>
) {
    val rows = (items.size + columns - 1) / columns // Calculate the required number of rows

    Column {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < items.size) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(Dimens.Padding4) // Adjust padding as needed
                        ) {
                            AnimatedTextItem(
                                lastTrainingSet = null,
                                set = items[index],
                                onEvent = {},
                                isAnimating = false,
                                isUsingTwoDumbbells = null,
                                modifier = Modifier
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f)) // Empty space for missing items
                    }
                }
            }
        }
    }
}

