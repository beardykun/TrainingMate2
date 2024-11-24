package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import androidx.compose.runtime.Immutable
import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.LocalBarParameters

data class SummaryScreenState(
    val weeklySummaryPieChatData: SummaryPieChatData? = null,
    val monthlySummaryPieChatData: SummaryPieChatData? = null,
    val summaryDataToDisplay: SummaryChatData? = null,
    val weeklySummaryBarChatData: SummaryBarChatData? = null,
    val monthlySummaryBarChatData: SummaryBarChatData? = null,
)

@Immutable
sealed interface SummaryChatData

@Immutable
data class SummaryPieChatData(
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
) : SummaryChatData

@Immutable
data class SummaryBarChatData(
    val barChatParams: List<List<LocalBarParameters>>
) : SummaryChatData