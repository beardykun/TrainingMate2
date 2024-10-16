package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import com.aay.compose.donutChart.model.PieChartData

data class SummaryScreenState(
    val weeklySummaryData: SummaryData? = null,
    val monthlySummaryData: SummaryData? = null,
    val summaryDataToDisplay:  SummaryData? = null
)

data class SummaryData(
    val trainingDuration: List<PieChartData>,
    val totalLiftedWeight: List<PieChartData>,
    val numWorkouts: List<PieChartData>,
    val numExercises: List<PieChartData>,
    val numSets: List<PieChartData>,
    val numReps: List<PieChartData>,
    val totalRestTime: List<PieChartData>,
    val avgDurationPerWorkout: List<PieChartData>,
    val avgLiftedWeightPerExercise: List<PieChartData>,
    val avgLiftedWeightPerWorkout: List<PieChartData>
)