package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnalysisViewModel(
    trainingDataSource: ITrainingDataSource,
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    exerciseDataSource: IExerciseDatasource,
    private val exerciseHistoryDatasource: IExerciseHistoryDatasource,

    ) : ViewModel() {

    private val trainingsData: MutableStateFlow<List<Training>?> = MutableStateFlow(null)
    private val _state: MutableStateFlow<AnalysisScreenSate> =
        MutableStateFlow(AnalysisScreenSate())
    val state =
        combine(
            _state,
            trainingsData,
            trainingDataSource.getTrainings(),
            exerciseDataSource.getAllExercises()
        ) { state, historyTrainings, localTrainings, localExercises ->
            state.copy(
                historyTrainings = historyTrainings,
                localTrainings = localTrainings,
                localExercises = localExercises.filter { it.bestLiftedWeight != 0.0 }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = AnalysisScreenSate()
        )

    fun onEvent(event: AnalysisScreenEvent) {
        when (event) {
            is AnalysisScreenEvent.OnMetricsModeChanged -> {
                _state.update {
                    it.copy(
                        metricsMode = event.metricsMode,
                        graphDisplayed = false,
                        metricsData = null
                    )
                }
                if (event.metricsMode == MetricsMode.GENERAL) {
                    onEvent(AnalysisScreenEvent.OnGeneralSelected)
                }
            }

            is AnalysisScreenEvent.OnExerciseNameSelected -> {
                _state.update {
                    it.copy(graphDisplayed = true)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    getExercisesWithName(event.exerciseName)
                }
            }

            AnalysisScreenEvent.OnGeneralSelected -> {
                _state.update {
                    it.copy(graphDisplayed = true)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    getGeneralTrainings()
                }
            }

            is AnalysisScreenEvent.OnTrainingIdSelected -> {
                _state.update {
                    it.copy(graphDisplayed = true)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    getExercisesForTraining(trainingId = event.trainingId)
                }
            }

            is AnalysisScreenEvent.OnAnalysisModeChanged -> {
                val analysisMode = when (event.analysisMode) {
                    AnalysisMode.WEIGHT.name -> AnalysisMode.WEIGHT
                    AnalysisMode.LENGTH.name -> AnalysisMode.LENGTH
                    AnalysisMode.PROGRESS.name -> AnalysisMode.PROGRESS
                    else -> AnalysisMode.WEIGHT
                }
                _state.update {
                    it.copy(
                        isDropdownExpanded = false,
                        analysisMode = analysisMode
                    )
                }
                viewModelScope.launch(Dispatchers.Default) {
                    prepareMetricsDataBasedOnAnalysisMode(
                        trainings = _state.value.historyTrainings,
                        analysisMode = analysisMode
                    )
                }
            }

            AnalysisScreenEvent.OnDropdownClosed -> {
                _state.update {
                    it.copy(isDropdownExpanded = false)
                }
            }

            AnalysisScreenEvent.OnDropdownOpen -> {
                _state.update {
                    it.copy(isDropdownExpanded = true)
                }
            }
        }
    }

    private suspend fun prepareMetricsDataBasedOnAnalysisMode(
        analysisMode: AnalysisMode,
        exercises: List<Exercise>? = null,
        trainings: List<Training>? = null
    ) {
        when (analysisMode) {
            AnalysisMode.WEIGHT -> {
                prepareWeightData(
                    historyExercises = exercises,
                    historyTrainings = trainings
                )
            }

            AnalysisMode.LENGTH -> {
                prepareLengthData(
                    historyTrainings = trainings
                )
            }

            AnalysisMode.PROGRESS -> {
                if (state.value.metricsMode == MetricsMode.EXERCISE) {
                    prepareProgressDataExercises(
                        historyExercises = exercises
                    )
                } else
                    prepareProgressDataTrainings(
                        historyTrainings = trainings
                    )
            }
        }
    }

    private suspend fun prepareProgressDataTrainings(
        historyTrainings: List<Training>? = null
    ) {
        historyTrainings?.let { trainings ->
            val weightList = trainings.map { it.totalWeightLifted }
            val weightBaseline = weightList.sum().div(weightList.size)
            val weightFivePercent = weightBaseline.div(20)

            val durationsList = trainings.map { Utils.trainingLengthInMin(it) }
            val durationsBaseline = durationsList.sum().div(durationsList.size)
            val durationFivePercent = durationsBaseline.div(20)

            val data = trainings.map { training ->
                val weightScore =
                    (training.totalWeightLifted - weightBaseline) / weightFivePercent

                val durationScore =
                    (Utils.trainingLengthInMin(training) - durationsBaseline) / durationFivePercent

                weightScore + durationScore
            }

            // Find the minimum value in the data list
            val minValue = data.minOrNull() ?: 0.0

            // Shift all values up so that the minimum value becomes zero
            val adjustedData = data.map { it - minValue }

            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        metricsData = adjustedData,
                        metricsXAxisData = trainings.map { tr -> tr.name }
                    )
                }
            }
        }
    }

    private suspend fun prepareProgressDataExercises(
        historyExercises: List<Exercise>? = null,
    ) {
        historyExercises?.let { exercises ->
            val weightList = exercises.map { it.totalLiftedWeight }
            val weightBaseline = weightList.sum().div(weightList.size)
            val weightFivePercent = weightBaseline.div(20)

            val setsList = exercises.map { it.sets.size }
            val setsBaseline = setsList.sum() / setsList.size

            val data = exercises.map { exercise ->
                val weightScore =
                    (exercise.totalLiftedWeight - weightBaseline) / weightFivePercent

                val setsScore = exercise.sets.size - setsBaseline

                weightScore + setsScore
            }

            // Find the minimum value in the data list
            val minValue = data.minOrNull() ?: 0.0

            // Shift all values up so that the minimum value becomes zero
            val adjustedData = data.map { it - minValue }

            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        metricsData = adjustedData,
                        metricsXAxisData = exercises.map { tr -> tr.name }
                    )
                }
            }
        }
    }


    private suspend fun prepareLengthData(
        historyTrainings: List<Training>? = null
    ) {
        val data = historyTrainings?.map { Utils.trainingLengthInMin(it) }
        val xAxisData = historyTrainings?.map { it.name }

        withContext(Dispatchers.Main) {
            _state.update {
                it.copy(
                    metricsData = data,
                    metricsXAxisData = xAxisData
                )
            }
        }
    }

    private suspend fun prepareWeightData(
        historyExercises: List<Exercise>? = null,
        historyTrainings: List<Training>? = null
    ) {
        val data = if (state.value.metricsMode == MetricsMode.EXERCISE) {
            historyExercises?.filter { it.totalLiftedWeight != 0.0 }?.map { it.totalLiftedWeight }
        } else historyTrainings?.map { it.totalWeightLifted }
        val xAxisData = if (state.value.metricsMode == MetricsMode.EXERCISE) {
            historyExercises?.filter { it.totalLiftedWeight != 0.0 }?.map { it.name }
        } else historyTrainings?.map { it.name }

        withContext(Dispatchers.Main) {

            _state.update {
                it.copy(
                    metricsData = data,
                    metricsXAxisData = xAxisData
                )
            }
        }
    }

    private suspend fun getGeneralTrainings() = viewModelScope.launch(Dispatchers.IO) {
        val trainings = trainingHistoryDataSource.getLatestHistoryTrainings().first()
        _state.update {
            it.copy(historyTrainings = trainings)
        }
        prepareMetricsDataBasedOnAnalysisMode(state.value.analysisMode, trainings = trainings)
    }

    private suspend fun getExercisesWithName(exerciseName: String) {
        val exercises = exerciseHistoryDatasource.getExercisesWithName(exerciseName).first()
        prepareMetricsDataBasedOnAnalysisMode(state.value.analysisMode, exercises = exercises)
    }

    private suspend fun getExercisesForTraining(trainingId: Long) {
        val trainings =
            trainingHistoryDataSource.getParticularTrainings(trainingTemplateId = trainingId)
                .first()

        _state.update {
            it.copy(historyTrainings = trainings)
        }

        val exercises =
            exerciseHistoryDatasource.getExercisesForTrainingWithId(trainingId = trainingId).first()
        prepareMetricsDataBasedOnAnalysisMode(
            state.value.analysisMode,
            exercises = exercises,
            trainings = trainings
        )
    }
}