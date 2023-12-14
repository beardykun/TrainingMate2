package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnalysisViewModel(
    private val trainingDataSource: ITrainingDataSource,
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    private val exerciseDataSource: IExerciseDatasource,
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
                localExercises = localExercises
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = AnalysisScreenSate()
        )

    init {
        onEvent(AnalysisScreenEvent.OnGeneralSelected)
    }

    fun onEvent(event: AnalysisScreenEvent) {
        when (event) {
            is AnalysisScreenEvent.OnMetricsModeChanged -> {
                _state.update {
                    it.copy(
                        metricsMode = event.metricsMode,
                        graphDisplayed = false
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
                getTrainingsWithExercise(event.exerciseName)
            }

            is AnalysisScreenEvent.OnGroupSelected -> {
                _state.update {
                    it.copy(graphDisplayed = true)
                }
                getGroupTrainings(event.group)
            }

            is AnalysisScreenEvent.OnTrainingSelected -> {
                getParticularTrainingExercises(event.trainingName)
                _state.update {
                    it.copy(graphDisplayed = true)
                }
            }

            AnalysisScreenEvent.OnGeneralSelected -> {
                getGeneralTrainings()
                _state.update {
                    it.copy(graphDisplayed = true)
                }
            }

            is AnalysisScreenEvent.OnTrainingIdSelected -> {
                getExercisesForTraining(trainingId = event.trainingId)
                _state.update {
                    it.copy(graphDisplayed = true)
                }
            }

            is AnalysisScreenEvent.OnAnalysisModeChanged -> {
                prepareMetricsDataBasedOnAnalysisMode(event.analysisMode)
            }
        }
    }

    private fun prepareMetricsDataBasedOnAnalysisMode(analysisMode: AnalysisMode) {
        when (analysisMode) {
            AnalysisMode.WEIGHT -> {
                prepareWeightData()
            }

            AnalysisMode.LENGTH -> {
                prepareLengthData()
            }

            AnalysisMode.PROGRESS -> {
                if (state.value.metricsMode == MetricsMode.EXERCISE) {
                    prepareProgressDataExercises()
                } else
                    prepareProgressDataTrainings()
            }
        }
    }

    private fun prepareProgressDataTrainings() = viewModelScope.launch(Dispatchers.Default) {
        state.value.historyTrainings?.let { trainings ->
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

            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        metricsData = data,
                        metricsXAxisData = trainings.map { tr -> tr.name }
                    )
                }
            }
        }
    }

    private fun prepareProgressDataExercises() = viewModelScope.launch(Dispatchers.Default) {
        state.value.historyExercises?.let { exercises ->
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

            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        metricsData = data,
                        metricsXAxisData = exercises.map { tr -> tr.name }
                    )
                }
            }
        }
    }

    private fun prepareLengthData() {
        val data = state.value.historyTrainings?.map { Utils.trainingLengthInMin(it) }
        val xAxisData = state.value.historyTrainings?.map { it.name }
        _state.update {
            it.copy(
                metricsData = data,
                metricsXAxisData = xAxisData
            )
        }
    }

    private fun prepareWeightData() {
        val data = if (state.value.metricsMode == MetricsMode.EXERCISE) {
            state.value.historyExercises?.map { it.totalLiftedWeight }
        } else state.value.historyTrainings?.map { it.totalWeightLifted }

        val xAxisData = if (state.value.metricsMode == MetricsMode.EXERCISE) {
            state.value.historyExercises?.map { it.name }
        } else state.value.historyTrainings?.map { it.name }

        _state.update {
            it.copy(
                metricsData = data,
                metricsXAxisData = xAxisData
            )
        }
    }

    private fun getGeneralTrainings() = viewModelScope.launch(Dispatchers.IO) {
        trainingHistoryDataSource.getLatestHistoryTrainings().collect { trainings ->
            trainingsData.value = trainings
        }
    }

    private fun getGroupTrainings(group: String) = viewModelScope.launch(Dispatchers.IO) {
        trainingHistoryDataSource.getGroupTrainings(group).collect { trainings ->
            trainingsData.value = trainings
        }
    }

    private fun getParticularTrainingExercises(trainingName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            exerciseHistoryDatasource.getExercisesWihName(trainingName).collect { exercises ->
                _state.update {
                    it.copy(
                        historyExercises = exercises
                    )
                }
            }
        }

    private fun getTrainingsWithExercise(exerciseName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            exerciseHistoryDatasource.getExercisesWihName(exerciseName).collect { exercises ->
                _state.update {
                    it.copy(historyExercises = exercises)
                }
            }
        }

    private fun getExercisesForTraining(trainingId: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            println("TAGGER $trainingId")

            exerciseHistoryDatasource.getExercisesForTrainingWithId(trainingId = trainingId)
                .collect { exercises ->
                    println("TAGGER $exercises")
                    _state.update {
                        it.copy(
                            historyExercises = exercises
                        )
                    }
                }
        }
}