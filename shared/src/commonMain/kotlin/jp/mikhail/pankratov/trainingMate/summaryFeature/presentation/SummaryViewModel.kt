package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import androidx.compose.ui.graphics.Color
import com.aay.compose.donutChart.model.PieChartData
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SummaryViewModel(summaryUseCaseProvider: SummaryUseCaseProvider) : ViewModel() {

    private val _state = MutableStateFlow(SummaryScreenState())

    val state = _state.onStart {
        val weeklySummary = summaryUseCaseProvider.getTwoLastWeeklySummaryUseCase().invoke().first()
        val monthlySummary =
            summaryUseCaseProvider.getTwoLastMonthlySummaryUseCase().invoke().first()

        val summaryData = fillStateWeeklySummaryData(
            weeklySummary,
            prefixLast = "Last Week:",
            prefixCurrent = "Current Week:"
        )
        _state.update {
            it.copy(
                weeklySummaryData = summaryData,
                summaryDataToDisplay = summaryData,
                monthlySummaryData = fillStateMonthlySummaryData(
                    monthlySummary,
                    prefixLast = "Last Month:",
                    prefixCurrent = "Current Month:"
                )
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = SummaryScreenState()
    )

    fun onEvent(event: SummaryScreenEvent) {
        when (event) {
            is SummaryScreenEvent.OnPageChanged -> {
                val summaryData = if (event.pageName == "Week") {
                    state.value.weeklySummaryData
                } else {
                    state.value.monthlySummaryData
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
    ): SummaryData {
        val first = summary.first()!!
        val last = if (summary.size > 1) summary.last() else null

        return SummaryData(
            trainingDuration = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.trainingDuration?.toDouble(),
                currentData = if (first.trainingDuration.toDouble() == 0.0) 1.0 else first.trainingDuration.toDouble(),
                unit = "min"
            ),
            totalLiftedWeight = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.totalLiftedWeight,
                currentData = first.totalLiftedWeight,
                unit = "kg"
            ),
            numWorkouts = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numWorkouts?.toDouble(),
                currentData = first.numWorkouts.toDouble(),
                unit = ""
            ),
            numExercises = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numExercises?.toDouble(),
                currentData = first.numExercises.toDouble(),
                unit = ""
            ),
            numSets = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numSets?.toDouble(),
                currentData = first.numSets.toDouble(),
                unit = ""
            ),
            numReps = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numReps?.toDouble(),
                currentData = first.numReps.toDouble(),
                unit = ""
            ),
            avgDurationPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.avgDurationPerWorkout,
                currentData = if (first.avgDurationPerWorkout == 0.0) 1.0 else first.avgDurationPerWorkout,
                unit = "min"
            ),
            avgLiftedWeightPerExercise = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.avgLiftedWeightPerExercise,
                currentData = first.avgLiftedWeightPerExercise,
                unit = "kg"
            ),
            avgLiftedWeightPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.avgLiftedWeightPerWorkout,
                currentData = first.avgLiftedWeightPerWorkout,
                unit = "kg"
            )
        )
    }

    private fun fillStateMonthlySummaryData(
        summary: List<MonthlySummary?>,
        prefixLast: String,
        prefixCurrent: String
    ): SummaryData {
        val first = summary.first()!!
        val last = if (summary.size > 1) summary.last() else null
        return SummaryData(
            trainingDuration = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.trainingDuration?.toDouble(),
                currentData = if (first.trainingDuration.toDouble() == 0.0) 1.0 else first.trainingDuration.toDouble(),
                unit = "min"
            ),
            totalLiftedWeight = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.totalLiftedWeight,
                currentData = first.totalLiftedWeight,
                unit = "kg"
            ),
            numWorkouts = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numWorkouts?.toDouble(),
                currentData = first.numWorkouts.toDouble(),
                unit = ""
            ),
            numExercises = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numExercises?.toDouble(),
                currentData = first.numExercises.toDouble(),
                unit = ""
            ),
            numSets = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numSets?.toDouble(),
                currentData = first.numSets.toDouble(),
                unit = ""
            ),
            numReps = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.numReps?.toDouble(),
                currentData = first.numReps.toDouble(),
                unit = ""
            ),
            avgDurationPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.avgDurationPerWorkout,
                currentData = if (first.avgDurationPerWorkout == 0.0) 1.0 else first.avgDurationPerWorkout,
                unit = "min"
            ),
            avgLiftedWeightPerExercise = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.avgLiftedWeightPerExercise,
                currentData = first.avgLiftedWeightPerExercise,
                unit = "kg"
            ),
            avgLiftedWeightPerWorkout = getPieChartData(
                prefixLast = prefixLast,
                prefixCurrent = prefixCurrent,
                lastData = last?.avgLiftedWeightPerWorkout,
                currentData = first.avgLiftedWeightPerWorkout,
                unit = "kg"
            )
        )
    }

    private fun getPieChartData(
        prefixLast: String,
        prefixCurrent: String,
        lastData: Double?,
        currentData: Double,
        unit: String
    ): List<PieChartData> {
        val current = PieChartData(
            partName = "$prefixCurrent ${currentData.toInt()} $unit",
            data = currentData,
            color = Color.Red
        )
        val last = if (lastData == null) null else
            PieChartData(
            partName = "$prefixLast ${lastData.toInt()} $unit",
            data = lastData,
            color = Color.Blue
        )
        return if (last == null)listOf(current) else listOf(last, current)
    }
}