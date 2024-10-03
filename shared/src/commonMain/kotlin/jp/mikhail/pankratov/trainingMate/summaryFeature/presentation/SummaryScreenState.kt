package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary

data class SummaryScreenState(
    val monthlySummary: List<MonthlySummary?>? = null,
    val weeklySummary: List<WeeklySummary?>? = null,
    val weeklySummaryData: SummaryData? = null,
    val monthlySummaryData: SummaryData? = null
)

data class SummaryData(
    val trainingDuration: List<PieChartData>,
    val totalLiftedWeight: List<PieChartData>,
    val numWorkouts: List<PieChartData>,
    val numExercises: List<PieChartData>,
    val numSets: List<PieChartData>,
    val numReps: List<PieChartData>,
    val avgDurationPerWorkout: List<PieChartData>,
    val avgLiftedWeightPerExercise: List<PieChartData>,
    val avgLiftedWeightPerWorkout: List<PieChartData>
)