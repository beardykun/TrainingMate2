package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonPieChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.MyHorizontalViewPager
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge

@Composable
fun SummaryScreen(state: SummaryScreenState, onEvent: (SummaryScreenEvent) -> Unit) {
    state.summaryDataToDisplay?.let { summaryItem ->
        MyHorizontalViewPager(
            pageNames = listOf(
                SharedRes.strings.week.getString(),
                SharedRes.strings.month.getString()
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
                        text = SharedRes.strings.not_enough_data.getString(),
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
                LazyColumn(modifier = Modifier.padding(all = Dimens.Padding16).fillMaxSize()) {
                    item {
                        ChartComp(
                            list = item.trainingDuration,
                            label = SharedRes.strings.total_training_duration.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.avgDurationPerWorkout,
                            label = SharedRes.strings.average_workout_time.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numWorkouts,
                            label = SharedRes.strings.number_of_workouts.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numExercises,
                            label = SharedRes.strings.number_of_done_exercises.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numSets,
                            label = SharedRes.strings.number_of_done_sets.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.numReps,
                            label = SharedRes.strings.total_reps_number.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.totalLiftedWeight,
                            label = SharedRes.strings.total_lifted_weight.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.avgLiftedWeightPerWorkout,
                            label = SharedRes.strings.average_weight_per_workout.getString()
                        )
                    }
                    item {
                        ChartComp(
                            list = item.avgLiftedWeightPerExercise,
                            label = SharedRes.strings.average_weight_per_exercise.getString()
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
        TextLarge(text = label)
        CommonPieChart(list = list, modifier = modifier.size(500.dp))
    }
}