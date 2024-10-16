package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonPieChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.MyHorizontalViewPager
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.average_weight_per_exercise_with_args
import maxrep.shared.generated.resources.average_weight_per_workout_with_args
import maxrep.shared.generated.resources.average_workout_time_with_args
import maxrep.shared.generated.resources.month
import maxrep.shared.generated.resources.not_enough_data
import maxrep.shared.generated.resources.number_of_done_exercises_with_args
import maxrep.shared.generated.resources.number_of_done_sets_with_args
import maxrep.shared.generated.resources.number_of_workouts_with_args
import maxrep.shared.generated.resources.total_reps_number_with_args
import maxrep.shared.generated.resources.total_rest_time
import maxrep.shared.generated.resources.total_training_duration_with_args
import maxrep.shared.generated.resources.total_weight_lifted_with_arg
import maxrep.shared.generated.resources.week
import org.jetbrains.compose.resources.stringResource

@Composable
fun SummaryScreen(state: SummaryScreenState, onEvent: (SummaryScreenEvent) -> Unit) {
    state.summaryDataToDisplay?.let { summaryItem ->
        MyHorizontalViewPager(
            pageNames = listOf(
                Res.string.week.getString(),
                Res.string.month.getString()
            ),
            onSelectionChanged = { item ->
                onEvent(SummaryScreenEvent.OnPageChanged(item))
            },
            item = summaryItem,
            onItemClick = {},
        ) { item, onItemSelected ->
            Column(modifier = Modifier.fillMaxWidth()) {
                AnimatedVisibility(visible = item.numExercises.size == 1) {
                    TextLarge(
                        text = Res.string.not_enough_data.getString(),
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        fontSize = Dimens.largeTextSize,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                LazyColumn(
                    modifier = Modifier.padding(all = Dimens.Padding16).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        ChartComp(
                            list = item.totalLiftedWeight,
                            label = stringResource(
                                Res.string.total_weight_lifted_with_arg,
                                getLabelValue(item.totalLiftedWeight)
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.trainingDuration,
                            label = stringResource(
                                Res.string.total_training_duration_with_args,
                                getLabelValue(item.trainingDuration).toInt()
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numWorkouts,
                            label = stringResource(
                                Res.string.number_of_workouts_with_args,
                                getLabelValue(item.numWorkouts).toInt()
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numExercises,
                            label = stringResource(
                                Res.string.number_of_done_exercises_with_args,
                                getLabelValue(item.numExercises).toInt()
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numSets,
                            label = stringResource(
                                Res.string.number_of_done_sets_with_args,
                                getLabelValue(item.numSets).toInt()
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numReps,
                            label = stringResource(
                                Res.string.total_reps_number_with_args,
                                getLabelValue(item.numReps).toInt()
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.totalRestTime,
                            label = stringResource(
                                Res.string.total_rest_time,
                                Utils.formatTimeText(getLabelValue(item.totalRestTime).toLong())
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.avgDurationPerWorkout,
                            label = stringResource(
                                Res.string.average_workout_time_with_args,
                                getLabelValue(item.avgDurationPerWorkout)
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.avgLiftedWeightPerWorkout,
                            label = stringResource(
                                Res.string.average_weight_per_workout_with_args,
                                getLabelValue(item.avgLiftedWeightPerWorkout)
                            )
                        )
                    }
                    item {
                        ChartComp(
                            list = item.avgLiftedWeightPerExercise,
                            label = stringResource(
                                Res.string.average_weight_per_exercise_with_args,
                                getLabelValue(item.avgLiftedWeightPerExercise)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChartComp(list: List<PieChartData>, label: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier) {
        val (icon, tint) = getLabelIcon(list)
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextLarge(text = label)
            Icon(imageVector = icon, contentDescription = null, tint = tint)
        }
        CommonPieChart(list = list, modifier = modifier.size(Dimens.PieChartSize))
    }
}

private fun getLabelIcon(list: List<PieChartData>): Pair<ImageVector, Color> {
    return if (list.size > 1) {
        val difference = list.last().data - list.first().data
        return when {
            difference > 0 -> Pair(Icons.AutoMirrored.Filled.TrendingUp, Color.Green)
            difference < 0 -> Pair(Icons.AutoMirrored.Filled.TrendingDown, Color.Red)
            else -> Pair(Icons.Filled.Remove, Color.Black)
        }
    } else Pair(Icons.Filled.Remove, Color.Black)
}

private fun getLabelValue(list: List<PieChartData>): Double {
    return if (list.size > 1) {
        list.last().data - list.first().data
    } else list.first().data
}