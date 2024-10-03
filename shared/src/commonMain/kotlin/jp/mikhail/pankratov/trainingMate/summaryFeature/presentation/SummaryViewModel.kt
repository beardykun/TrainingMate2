package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import androidx.compose.ui.graphics.Color
import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SummaryViewModel(summaryUseCaseProvider: SummaryUseCaseProvider) : ViewModel() {

    private val _state = MutableStateFlow(SummaryScreenState())

    val state = combine(
        _state,
        summaryUseCaseProvider.getTwoLastWeeklySummaryUseCase().invoke(),
        summaryUseCaseProvider.getTwoLastMonthlySummaryUseCase().invoke()
    ) { state, weeklySummaries, monthlySummaries ->
        state.copy(
            monthlySummary = monthlySummaries,
            weeklySummary = weeklySummaries
        )
    }.map { state ->
        var newState = if (state.weeklySummary != null) {
            state.copy(
                weeklySummaryData = fillStateWeeklySummaryData(
                    state.weeklySummary,
                    prefixLast = "Last Week:",
                    prefixCurrent = "Current Week:"
                )
            )
        } else {
            state
        }
        newState = if (state.monthlySummary != null) {
            newState.copy(
                monthlySummaryData = fillStateMonthlySummaryData(
                    state.monthlySummary,
                    prefixLast = "Last Month:",
                    prefixCurrent = "Current Month:"
                )
            )
        } else newState
        newState
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = SummaryScreenState()
    )

    private fun fillStateWeeklySummaryData(
        summary: List<WeeklySummary?>,
        prefixLast: String,
        prefixCurrent: String
    ): SummaryData {
        val first = summary.first()
        val last = summary.last()
        return SummaryData(
            trainingDuration = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.trainingDuration?.toDouble() ?: 0.0,
                currentData = last?.trainingDuration?.toDouble() ?: 0.0,
                unit = "min"
            ),
            totalLiftedWeight = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.totalLiftedWeight ?: 0.0,
                currentData = last?.totalLiftedWeight ?: 0.0,
                unit = "kg"
            ),
            numWorkouts = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numWorkouts?.toDouble() ?: 0.0,
                currentData = last?.numWorkouts?.toDouble() ?: 0.0,
                unit = ""
            ),
            numExercises = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numExercises?.toDouble() ?: 0.0,
                currentData = last?.numExercises?.toDouble() ?: 0.0,
                unit = ""
            ),
            numSets = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numSets?.toDouble() ?: 0.0,
                currentData = last?.numSets?.toDouble() ?: 0.0,
                unit = ""
            ),
            numReps = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numReps?.toDouble() ?: 0.0,
                currentData = last?.numReps?.toDouble() ?: 0.0,
                unit = ""
            ),
            avgDurationPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.avgDurationPerWorkout ?: 0.0,
                currentData = last?.avgDurationPerWorkout ?: 0.0,
                unit = "min"
            ),
            avgLiftedWeightPerExercise = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.avgLiftedWeightPerExercise ?: 0.0,
                currentData = last?.avgLiftedWeightPerExercise ?: 0.0,
                unit = "kg"
            ),
            avgLiftedWeightPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.avgLiftedWeightPerWorkout ?: 0.0,
                currentData = last?.avgLiftedWeightPerWorkout ?: 0.0,
                unit = "kg"
            )
        )
    }

    private fun fillStateMonthlySummaryData(
        summary: List<MonthlySummary?>,
        prefixLast: String,
        prefixCurrent: String
    ): SummaryData {
        val first = summary.first()
        val last = summary.last()
        return SummaryData(
            trainingDuration = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.trainingDuration?.toDouble() ?: 0.0,
                currentData = last?.trainingDuration?.toDouble() ?: 0.0,
                unit = "min"
            ),
            totalLiftedWeight = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.totalLiftedWeight ?: 0.0,
                currentData = last?.totalLiftedWeight ?: 0.0,
                unit = "kg"
            ),
            numWorkouts = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numWorkouts?.toDouble() ?: 0.0,
                currentData = last?.numWorkouts?.toDouble() ?: 0.0,
                unit = ""
            ),
            numExercises = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numExercises?.toDouble() ?: 0.0,
                currentData = last?.numExercises?.toDouble() ?: 0.0,
                unit = ""
            ),
            numSets = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numSets?.toDouble() ?: 0.0,
                currentData = last?.numSets?.toDouble() ?: 0.0,
                unit = ""
            ),
            numReps = getPieChartData(
                 prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.numReps?.toDouble() ?: 0.0,
                currentData = last?.numReps?.toDouble() ?: 0.0,
                unit = ""
            ),
            avgDurationPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.avgDurationPerWorkout ?: 0.0,
                currentData = last?.avgDurationPerWorkout ?: 0.0,
                unit = "min"
            ),
            avgLiftedWeightPerExercise = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.avgLiftedWeightPerExercise ?: 0.0,
                currentData = last?.avgLiftedWeightPerExercise ?: 0.0,
                unit = "kg"
            ),
            avgLiftedWeightPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = first?.avgLiftedWeightPerWorkout ?: 0.0,
                currentData = last?.avgLiftedWeightPerWorkout ?: 0.0,
                unit = "kg"
            )
        )
    }

    private fun getPieChartData(
        prefixLast: String,
        prefixCurrent: String,
        lastData: Double,
        currentData: Double,
        unit: String
    ): List<PieChartData> {
        return listOf(
            PieChartData(
                partName = "$prefixLast ${lastData.toInt()} $unit",
                data = lastData,
                color = Color.Blue
            ),
            PieChartData(
                partName = "$prefixCurrent ${currentData.toInt()} $unit",
                data = currentData,
                color = Color.Red
            )
        )
    }
}