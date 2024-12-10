package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import Dimens
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
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TopAppBarScaffold
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.Utils
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables.AnimatedTextItem
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.date
import maxrep.shared.generated.resources.lifted_weight_with_arg
import maxrep.shared.generated.resources.sets
import maxrep.shared.generated.resources.total_weight_lifted_with_arg
import maxrep.shared.generated.resources.training_duration_with_arg
import maxrep.shared.generated.resources.training_groups_with_arg
import maxrep.shared.generated.resources.training_name_with_arg
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistoryInfoScreen(
    state: HistoryInfoState,
    navigator: Navigator
) {
    TopAppBarScaffold(
        label = Routs.HistoryScreens.historyInfo,
        onBackPressed = { navigator.goBack() },
        content = {
            Column(modifier = Modifier.fillMaxSize().padding(all = Dimens.Padding16)) {
                state.training?.let { training ->
                    TextLarge(
                        text = stringResource(
                            Res.string.training_name_with_arg,
                            training.name
                        )
                    )
                    training.startTime?.let { startTime ->
                        HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
                        Spacer(modifier = Modifier.height(Dimens.Padding8))
                        TextLarge(
                            text = stringResource(
                                Res.string.date,
                                Utils.formatEpochMillisToDate(startTime)
                            )
                        )
                    }
                    HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
                    Spacer(modifier = Modifier.height(Dimens.Padding8))
                    TextLarge(
                        text = stringResource(
                            Res.string.training_groups_with_arg, training.groups
                        )
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
                    Spacer(modifier = Modifier.height(Dimens.Padding8))
                    TextLarge(
                        text = stringResource(
                            Res.string.training_duration_with_arg, Utils.countTrainingTime(training)
                        )
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = Dimens.dividerHeight)
                    Spacer(modifier = Modifier.height(Dimens.Padding8))
                    TextLarge(
                        text = stringResource(
                            Res.string.total_weight_lifted_with_arg, training.totalLiftedWeight
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
                                    .animateItem()
                            )
                        }
                    }
                }
            }
        })
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
                text = Res.string.sets.getString()
            )
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            FixedGrid(columns = 3, items = exercise.sets)
            Spacer(modifier = Modifier.height(Dimens.Padding8))
            TextMedium(
                text = stringResource(
                    Res.string.lifted_weight_with_arg,
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

