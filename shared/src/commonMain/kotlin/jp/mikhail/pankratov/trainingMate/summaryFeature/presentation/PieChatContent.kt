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
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonPieChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.theme.darkGreen
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.average_weight_per_exercise_with_args
import maxrep.shared.generated.resources.average_weight_per_workout_with_args
import maxrep.shared.generated.resources.average_workout_time_with_args
import maxrep.shared.generated.resources.not_enough_data
import maxrep.shared.generated.resources.number_of_done_exercises_with_args
import maxrep.shared.generated.resources.number_of_done_sets_with_args
import maxrep.shared.generated.resources.number_of_workouts_with_args
import maxrep.shared.generated.resources.total_reps_number_with_args
import maxrep.shared.generated.resources.total_rest_time
import maxrep.shared.generated.resources.total_training_duration_with_args
import maxrep.shared.generated.resources.total_weight_lifted_with_arg
import org.jetbrains.compose.resources.stringResource

@Composable
fun PieChatContent(summaryItem: SummaryPieChatData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(visible = summaryItem.numExercises.size == 1) {
            TextLarge(
                text = Res.string.not_enough_data.getString(),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        LazyColumn(
            modifier = Modifier.padding(all = Dimens.Padding16).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ChartComp(
                    list = summaryItem.totalLiftedWeight,
                    label = stringResource(
                        Res.string.total_weight_lifted_with_arg,
                        getLabelValue(summaryItem.totalLiftedWeight)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.trainingDuration,
                    label = stringResource(
                        Res.string.total_training_duration_with_args,
                        getLabelValue(summaryItem.trainingDuration)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.numWorkouts,
                    label = stringResource(
                        Res.string.number_of_workouts_with_args,
                        getLabelValue(summaryItem.numWorkouts)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.numExercises,
                    label = stringResource(
                        Res.string.number_of_done_exercises_with_args,
                        getLabelValue(summaryItem.numExercises)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.numSets,
                    label = stringResource(
                        Res.string.number_of_done_sets_with_args,
                        getLabelValue(summaryItem.numSets)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.numReps,
                    label = stringResource(
                        Res.string.total_reps_number_with_args,
                        getLabelValue(summaryItem.numReps)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.totalRestTime,
                    label = stringResource(
                        Res.string.total_rest_time,
                        getLabelValue(summaryItem.totalRestTime)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.avgDurationPerWorkout,
                    label = stringResource(
                        Res.string.average_workout_time_with_args,
                        getLabelValue(summaryItem.avgDurationPerWorkout)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.avgLiftedWeightPerWorkout,
                    label = stringResource(
                        Res.string.average_weight_per_workout_with_args,
                        getLabelValue(summaryItem.avgLiftedWeightPerWorkout)
                    )
                )
            }
            item {
                ChartComp(
                    list = summaryItem.avgLiftedWeightPerExercise,
                    label = stringResource(
                        Res.string.average_weight_per_exercise_with_args,
                        getLabelValue(summaryItem.avgLiftedWeightPerExercise)
                    )
                )
            }
        }
    }
}


private fun getLabelIcon(list: List<PieChartData>): Pair<ImageVector, Color> {
    return if (list.size > 1) {
        val difference = list.last().data - list.first().data
        return when {
            difference > 0 -> Pair(Icons.AutoMirrored.Filled.TrendingUp, darkGreen)
            difference < 0 -> Pair(Icons.AutoMirrored.Filled.TrendingDown, Color.Red)
            else -> Pair(Icons.Filled.Remove, Color.Black)
        }
    } else Pair(Icons.Filled.Remove, Color.Black)
}

private fun getLabelValue(list: List<PieChartData>): Int {
    return if (list.size > 1) {
        (list.last().data - list.first().data).toInt()
    } else list.first().data.toInt()
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