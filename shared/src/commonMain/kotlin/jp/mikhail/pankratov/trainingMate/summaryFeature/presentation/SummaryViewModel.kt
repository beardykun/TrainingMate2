package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.presentation.UiText
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.LocalBarParameters
import jp.mikhail.pankratov.trainingMate.theme.Amber
import jp.mikhail.pankratov.trainingMate.theme.AvgDurationPerWorkout
import jp.mikhail.pankratov.trainingMate.theme.AvgLiftedWeightPerExercise
import jp.mikhail.pankratov.trainingMate.theme.AvgLiftedWeightPerWorkout
import jp.mikhail.pankratov.trainingMate.theme.Blue
import jp.mikhail.pankratov.trainingMate.theme.BlueGray
import jp.mikhail.pankratov.trainingMate.theme.Brown
import jp.mikhail.pankratov.trainingMate.theme.DeepOrange
import jp.mikhail.pankratov.trainingMate.theme.Green
import jp.mikhail.pankratov.trainingMate.theme.LightAmber
import jp.mikhail.pankratov.trainingMate.theme.LightBlue
import jp.mikhail.pankratov.trainingMate.theme.LightBlueAlt
import jp.mikhail.pankratov.trainingMate.theme.LightBlueGray
import jp.mikhail.pankratov.trainingMate.theme.LightBrown
import jp.mikhail.pankratov.trainingMate.theme.LightGreen
import jp.mikhail.pankratov.trainingMate.theme.LightOrange
import jp.mikhail.pankratov.trainingMate.theme.LightOrangeAlt
import jp.mikhail.pankratov.trainingMate.theme.LightPurple
import jp.mikhail.pankratov.trainingMate.theme.LightTeal
import jp.mikhail.pankratov.trainingMate.theme.LighterBlue
import jp.mikhail.pankratov.trainingMate.theme.NumExercises
import jp.mikhail.pankratov.trainingMate.theme.NumReps
import jp.mikhail.pankratov.trainingMate.theme.NumSets
import jp.mikhail.pankratov.trainingMate.theme.NumWorkouts
import jp.mikhail.pankratov.trainingMate.theme.Orange
import jp.mikhail.pankratov.trainingMate.theme.Purple
import jp.mikhail.pankratov.trainingMate.theme.ScoreAverage
import jp.mikhail.pankratov.trainingMate.theme.ScoreBest
import jp.mikhail.pankratov.trainingMate.theme.ScoreMin
import jp.mikhail.pankratov.trainingMate.theme.Teal
import jp.mikhail.pankratov.trainingMate.theme.TotalLiftedWeight
import jp.mikhail.pankratov.trainingMate.theme.TotalRestTime
import jp.mikhail.pankratov.trainingMate.theme.TrainingDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.average_training_score
import maxrep.shared.generated.resources.average_weight_per_exercise
import maxrep.shared.generated.resources.average_weight_per_workout
import maxrep.shared.generated.resources.average_workout_time
import maxrep.shared.generated.resources.best_training_score
import maxrep.shared.generated.resources.min_training_score
import maxrep.shared.generated.resources.number_of_done_exercises
import maxrep.shared.generated.resources.number_of_done_sets
import maxrep.shared.generated.resources.number_of_workouts
import maxrep.shared.generated.resources.total_lifted_weight
import maxrep.shared.generated.resources.total_reps_number
import maxrep.shared.generated.resources.total_rest
import maxrep.shared.generated.resources.total_training_duration

private const val WEEK_LAST_PREFIX_INDEX = 0
private const val WEEK_CURRENT_PREFIX_INDEX = 1
private const val MONTH_LAST_PREFIX_INDEX = 2
private const val MONTH_CURRENT_PREFIX_INDEX = 3
private const val LIMIT: Long = 30
private const val MIN_LIST_SIZE = 2

class SummaryViewModel(
    summaryUseCaseProvider: SummaryUseCaseProvider,
    private val stringsToPass: List<String>
) : ViewModel() {

    private val _state = MutableStateFlow(SummaryScreenState())

    val state = _state.onStart {
        getSummariesData(summaryUseCaseProvider)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = SummaryScreenState()
    )

    private suspend fun getSummariesData(summaryUseCaseProvider: SummaryUseCaseProvider) {
        val weeklySummary =
            summaryUseCaseProvider.getLastWeeklySummariesUseCase().invoke(limit = LIMIT).first()
        val monthlySummary =
            summaryUseCaseProvider.getLastMonthlySummariesUseCase().invoke(limit = LIMIT).first()

        val weeklySummaryData = fillStateWeeklySummaryData(
            summary = if (weeklySummary.size >= MIN_LIST_SIZE) {
                weeklySummary.subList(weeklySummary.size - MIN_LIST_SIZE, weeklySummary.size)
            } else {
                weeklySummary
            },
            prefixLast = stringsToPass[WEEK_LAST_PREFIX_INDEX],
            prefixCurrent = stringsToPass[WEEK_CURRENT_PREFIX_INDEX]
        )
        val monthlySummaryData = fillStateMonthlySummaryData(
            summary = if (monthlySummary.size >= MIN_LIST_SIZE) {
                monthlySummary.subList(monthlySummary.size - MIN_LIST_SIZE, monthlySummary.size)
            } else {
                monthlySummary
            },
            prefixLast = stringsToPass[MONTH_LAST_PREFIX_INDEX],
            prefixCurrent = stringsToPass[MONTH_CURRENT_PREFIX_INDEX]
        )

        _state.update { state ->
            state.copy(
                weeklySummaryPieChatData = weeklySummaryData,
                summaryDataToDisplay = weeklySummaryData,
                monthlySummaryPieChatData = monthlySummaryData,
                weeklySummaryBarChatData = prepareWeeklyBarData(weeklySummary),
                monthlySummaryBarChatData = prepareMonthlyBarData(monthlySummary)
            )
        }
    }

    private fun prepareWeeklyBarData(weeklySummary: List<WeeklySummary?>) =
        SummaryBarChatData(
            barChatParams = listOf(
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.trainingDuration?.toDouble() },
                        barColor = TrainingDuration, // Green for time-related metrics
                        dataName = UiText.StringResourceId(id = Res.string.total_training_duration),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.totalLiftedWeight },
                        barColor = TotalLiftedWeight, // Deep Orange for weight
                        dataName = UiText.StringResourceId(id = Res.string.total_lifted_weight),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.numWorkouts?.toDouble() },
                        barColor = NumWorkouts, // Blue for workout count
                        dataName = UiText.StringResourceId(id = Res.string.number_of_workouts),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.numExercises?.toDouble() },
                        barColor = NumExercises, // Amber for exercise count
                        dataName = UiText.StringResourceId(id = Res.string.number_of_done_exercises),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.numSets?.toDouble() },
                        barColor = NumSets, // Purple for sets
                        dataName = UiText.StringResourceId(id = Res.string.number_of_done_sets),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.numReps?.toDouble() },
                        barColor = NumReps, // Light Blue for reps
                        dataName = UiText.StringResourceId(id = Res.string.total_reps_number),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.totalRestTime?.toDouble()?.div(60) },
                        barColor = TotalRestTime, // Brown for rest time
                        dataName = UiText.StringResourceId(id = Res.string.total_rest),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.avgDurationPerWorkout },
                        barColor = AvgDurationPerWorkout, // Blue Gray for average duration
                        dataName = UiText.StringResourceId(id = Res.string.average_workout_time),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.avgLiftedWeightPerExercise },
                        barColor = AvgLiftedWeightPerExercise, // Teal for average weight per exercise
                        dataName = UiText.StringResourceId(id = Res.string.average_weight_per_exercise),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.avgLiftedWeightPerWorkout },
                        barColor = AvgLiftedWeightPerWorkout, // Orange for average weight per workout
                        dataName = UiText.StringResourceId(id = Res.string.average_weight_per_workout),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.averageTrainingScore?.toDouble() },
                        barColor = ScoreAverage, // Light Blue for average score
                        dataName = UiText.StringResourceId(id = Res.string.average_training_score),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.bestTrainingScore?.toDouble() },
                        barColor = ScoreBest, // Green for best score
                        dataName = UiText.StringResourceId(id = Res.string.best_training_score),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = weeklySummary.mapNotNull { it?.minTrainingScore?.toDouble() },
                        barColor = ScoreMin, // Red for min score
                        dataName = UiText.StringResourceId(id = Res.string.min_training_score),
                    )
                )
            )
        )

    private fun prepareMonthlyBarData(monthlySummary: List<MonthlySummary?>) =
        SummaryBarChatData(
            barChatParams = listOf(
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.trainingDuration?.toDouble() },
                        barColor = Color(0xFF4CAF50), // Green for time-related metrics
                        dataName = UiText.StringResourceId(id = Res.string.total_training_duration),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.totalLiftedWeight },
                        barColor = Color(0xFFFF5722), // Deep Orange for weight
                        dataName = UiText.StringResourceId(id = Res.string.total_lifted_weight),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.numWorkouts?.toDouble() },
                        barColor = Color(0xFF2196F3), // Blue for workout count
                        dataName = UiText.StringResourceId(id = Res.string.number_of_workouts),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.numExercises?.toDouble() },
                        barColor = Color(0xFFFFC107), // Amber for exercise count
                        dataName = UiText.StringResourceId(id = Res.string.number_of_done_exercises),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.numSets?.toDouble() },
                        barColor = Color(0xFF9C27B0), // Purple for sets
                        dataName = UiText.StringResourceId(id = Res.string.number_of_done_sets),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.numReps?.toDouble() },
                        barColor = Color(0xFF03A9F4), // Light Blue for reps
                        dataName = UiText.StringResourceId(id = Res.string.total_reps_number),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.totalRestTime?.toDouble()?.div(60) },
                        barColor = Color(0xFF795548), // Brown for rest time
                        dataName = UiText.StringResourceId(id = Res.string.total_rest),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.avgDurationPerWorkout },
                        barColor = Color(0xFF607D8B), // Blue Gray for average duration
                        dataName = UiText.StringResourceId(id = Res.string.average_workout_time),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.avgLiftedWeightPerExercise },
                        barColor = Color(0xFF009688), // Teal for average weight per exercise
                        dataName = UiText.StringResourceId(id = Res.string.average_weight_per_exercise),
                    )
                ),
                listOf(
                    LocalBarParameters(
                        data = monthlySummary.mapNotNull { it?.avgLiftedWeightPerWorkout },
                        barColor = Color(0xFFFF9800), // Orange for average weight per workout
                        dataName = UiText.StringResourceId(id = Res.string.average_weight_per_workout),
                    )
                )
            )
        )

    fun onEvent(event: SummaryScreenEvent) {
        when (event) {
            is SummaryScreenEvent.OnTabChanged -> {
                val summaryData = when (event.tab) {
                    SummaryTabs.WEEK -> {
                        state.value.weeklySummaryPieChatData
                    }

                    SummaryTabs.MONTH -> {
                        state.value.monthlySummaryPieChatData
                    }

                    SummaryTabs.WEEK_ALL -> {
                        state.value.weeklySummaryBarChatData
                    }

                    SummaryTabs.MONTH_ALL -> {
                        state.value.monthlySummaryBarChatData
                    }
                }
                _state.update {
                    it.copy(
                        summaryDataToDisplay = summaryData
                    )
                }
            }
        }
    }

    private fun fillStateWeeklySummaryData(
        summary: List<WeeklySummary?>,
        prefixLast: String,
        prefixCurrent: String
    ): SummaryPieChatData {
        val currentData = if (summary.size > 1) summary.last()!! else summary.first()!!
        val lastData = summary.first()

        return SummaryPieChatData(
            trainingDuration = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.trainingDuration?.toDouble(),
                currentData = if (currentData.trainingDuration.toDouble() == 0.0) 1.0 else currentData.trainingDuration.toDouble(),
                unit = "min",
                colorCurrent = Green,
                colorLast = LightGreen
            ),
            totalLiftedWeight = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.totalLiftedWeight,
                currentData = if (currentData.totalLiftedWeight == 0.0) 1.0 else currentData.totalLiftedWeight,
                unit = "kg",
                colorCurrent = DeepOrange,
                colorLast = LightOrange
            ),
            numWorkouts = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numWorkouts?.toDouble(),
                currentData = currentData.numWorkouts.toDouble(),
                unit = "",
                colorCurrent = Blue,
                colorLast = LightBlue
            ),
            numExercises = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numExercises?.toDouble(),
                currentData = currentData.numExercises.toDouble(),
                unit = "",
                colorCurrent = Amber,
                colorLast = LightAmber
            ),
            numSets = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numSets?.toDouble(),
                currentData = currentData.numSets.toDouble(),
                unit = "",
                colorCurrent = Purple,
                colorLast = LightPurple
            ),
            numReps = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numReps?.toDouble(),
                currentData = currentData.numReps.toDouble(),
                unit = "",
                colorCurrent = LightBlueAlt,
                colorLast = LighterBlue
            ),
            totalRestTime = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.totalRestTime?.toDouble()?.div(60),
                currentData = currentData.totalRestTime.toDouble().div(60),
                unit = "min",
                colorCurrent = Brown,
                colorLast = LightBrown
            ),
            avgDurationPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.avgDurationPerWorkout,
                currentData = if (currentData.avgDurationPerWorkout == 0.0) 1.0 else currentData.avgDurationPerWorkout,
                unit = "min",
                colorCurrent = BlueGray,
                colorLast = LightBlueGray
            ),
            avgLiftedWeightPerExercise = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.avgLiftedWeightPerExercise,
                currentData = if (currentData.avgLiftedWeightPerExercise == 0.0) 1.0 else currentData.avgLiftedWeightPerExercise,
                unit = "kg",
                colorCurrent = Teal,
                colorLast = LightTeal
            ),
            avgLiftedWeightPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.avgLiftedWeightPerWorkout,
                currentData = if (currentData.avgLiftedWeightPerWorkout == 0.0) 1.0 else currentData.avgLiftedWeightPerWorkout,
                unit = "kg",
                colorCurrent = Orange,
                colorLast = LightOrangeAlt
            ),
            avgTrainingScore = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.averageTrainingScore?.toDouble(),
                currentData = currentData.averageTrainingScore.toDouble(),
                unit = "pts",
                colorCurrent = ScoreAverage,
                colorLast = ScoreAverage.copy(alpha = 0.6f)
            ),
            bestTrainingScore = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.bestTrainingScore?.toDouble(),
                currentData = currentData.bestTrainingScore.toDouble(),
                unit = "pts",
                colorCurrent = ScoreBest,
                colorLast = ScoreBest.copy(alpha = 0.6f)
            ),
            minTrainingScore = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.minTrainingScore?.toDouble(),
                currentData = currentData.minTrainingScore.toDouble(),
                unit = "pts",
                colorCurrent = ScoreMin,
                colorLast = ScoreMin.copy(alpha = 0.6f)
            )
        )
    }

    private fun fillStateMonthlySummaryData(
        summary: List<MonthlySummary?>,
        prefixLast: String,
        prefixCurrent: String
    ): SummaryPieChatData {
        val currentData = if (summary.size > 1) summary.last()!! else summary.first()!!
        val lastData = summary.first()
        return SummaryPieChatData(
            trainingDuration = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.trainingDuration?.toDouble(),
                currentData = if (currentData.trainingDuration.toDouble() == 0.0) 1.0 else currentData.trainingDuration.toDouble(),
                unit = "min",
                colorCurrent = Green,
                colorLast = LightGreen
            ),
            totalLiftedWeight = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.totalLiftedWeight,
                currentData = if (currentData.totalLiftedWeight == 0.0) 1.0 else currentData.totalLiftedWeight,
                unit = "kg",
                colorCurrent = DeepOrange,
                colorLast = LightOrange
            ),
            numWorkouts = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numWorkouts?.toDouble(),
                currentData = currentData.numWorkouts.toDouble(),
                unit = "",
                colorCurrent = Blue,
                colorLast = LightBlue
            ),
            numExercises = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numExercises?.toDouble(),
                currentData = currentData.numExercises.toDouble(),
                unit = "",
                colorCurrent = Amber,
                colorLast = LightAmber
            ),
            numSets = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numSets?.toDouble(),
                currentData = currentData.numSets.toDouble(),
                unit = "",
                colorCurrent = Purple,
                colorLast = LightPurple
            ),
            numReps = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.numReps?.toDouble(),
                currentData = currentData.numReps.toDouble(),
                unit = "",
                colorCurrent = LightBlueAlt,
                colorLast = LighterBlue
            ),
            totalRestTime = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.totalRestTime?.toDouble()?.div(60),
                currentData = currentData.totalRestTime.toDouble().div(60),
                unit = "min",
                colorCurrent = Brown,
                colorLast = LightBrown
            ),
            avgDurationPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.avgDurationPerWorkout,
                currentData = if (currentData.avgDurationPerWorkout == 0.0) 1.0 else currentData.avgDurationPerWorkout,
                unit = "min",
                colorCurrent = BlueGray,
                colorLast = LightBlueGray
            ),
            avgLiftedWeightPerExercise = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.avgLiftedWeightPerExercise,
                currentData = if (currentData.avgLiftedWeightPerExercise == 0.0) 1.0 else currentData.avgLiftedWeightPerExercise,
                unit = "kg",
                colorCurrent = Teal,
                colorLast = LightTeal
            ),
            avgLiftedWeightPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.avgLiftedWeightPerWorkout,
                currentData = if (currentData.avgLiftedWeightPerWorkout == 0.0) 1.0 else currentData.avgLiftedWeightPerWorkout,
                unit = "kg",
                colorCurrent = Orange,
                colorLast = LightOrangeAlt
            ),
            avgTrainingScore = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.averageTrainingScore?.toDouble(),
                currentData = currentData.averageTrainingScore.toDouble(),
                unit = "pts",
                colorCurrent = ScoreAverage,
                colorLast = ScoreAverage.copy(alpha = 0.6f)
            ),
            bestTrainingScore = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.bestTrainingScore?.toDouble(),
                currentData = currentData.bestTrainingScore.toDouble(),
                unit = "pts",
                colorCurrent = ScoreBest,
                colorLast = ScoreBest.copy(alpha = 0.6f)
            ),
            minTrainingScore = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = lastData?.minTrainingScore?.toDouble(),
                currentData = currentData.minTrainingScore.toDouble(),
                unit = "pts",
                colorCurrent = ScoreMin,
                colorLast = ScoreMin.copy(alpha = 0.6f)
            )
        )
    }

    private fun getPieChartData(
        prefixLast: String,
        prefixCurrent: String,
        lastData: Double?,
        currentData: Double,
        unit: String,
        colorCurrent: Color,
        colorLast: Color
    ): List<PieChartData> {
        val current = PieChartData(
            partName = "$prefixCurrent ${currentData.toInt()} $unit",
            data = currentData,
            color = colorCurrent
        )
        val last = if (lastData == null) null else
            PieChartData(
                partName = "$prefixLast ${lastData.toInt()} $unit",
                data = lastData,
                color = colorLast
            )
        return if (last == null) listOf(current) else listOf(last, current)
    }
}