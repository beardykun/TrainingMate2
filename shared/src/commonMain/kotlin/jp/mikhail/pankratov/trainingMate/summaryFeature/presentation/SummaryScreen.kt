package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonPieChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge

@Composable
fun SummaryScreen(state: SummaryScreenState) {
    println("TAGGER ${state.weeklySummaryData}")
    state.weeklySummaryData?.let {
        LazyColumn(modifier = Modifier.padding(all = Dimens.Padding16).fillMaxSize()) {
            item {
                ChartComp(list = it.trainingDuration, label = "Training Duration")
            }
            item {
                ChartComp(list = it.avgDurationPerWorkout, label = "Avg Duration Per Workout")
            }
            item {
                ChartComp(list = it.numWorkouts, label = "Number of Workouts")
            }
            item {
                ChartComp(list = it.numExercises, label = "Number of Exercises")
            }
            item {
                ChartComp(list = it.numSets, label = "Number of Sets")
            }
            item {
                ChartComp(list = it.numReps, label = "Number of Reps")
            }
            item {
                ChartComp(list = it.totalLiftedWeight, label = "Total Lifted Weight")
            }
            item {
                ChartComp(list = it.avgLiftedWeightPerWorkout, label = "Avg Lifted Weight Per Workout")
            }
            item {
                ChartComp(list = it.avgLiftedWeightPerExercise, label = "Avg Lifted Weight Per Exercise")
            }
        }
    }
}

@Composable
fun ChartComp(list: List<PieChartData>, label: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier) {
        TextLarge(text = label)
        CommonPieChart(list = list, modifier = modifier.size(400.dp))
    }
}