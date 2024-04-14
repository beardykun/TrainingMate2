package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnalysisViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider
) : ViewModel() {

    private val trainingsData: MutableStateFlow<List<Training>?> = MutableStateFlow(null)
    private val _state: MutableStateFlow<AnalysisScreenSate> =
        MutableStateFlow(AnalysisScreenSate())
    val state =
        combine(
            _state,
            trainingsData,
            trainingUseCaseProvider.getLocalTrainingsUseCase().invoke(),
            exerciseUseCaseProvider.getAllLocalExercisesUseCase().invoke()
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
                val analysisMode =
                    if (event.metricsMode == MetricsMode.EXERCISE &&
                        state.value.analysisMode == AnalysisMode.LENGTH
                    )
                        AnalysisMode.WEIGHT else state.value.analysisMode

                _state.update {
                    it.copy(
                        metricsMode = event.metricsMode,
                        graphDisplayed = false,
                        metricsData = null,
                        historyExercises = null,
                        historyTrainings = null,
                        analysisMode = analysisMode
                    )
                }
                if (event.metricsMode == MetricsMode.GENERAL) {
                    onEvent(AnalysisScreenEvent.OnGeneralSelected)
                }
            }

            is AnalysisScreenEvent.OnExerciseNameSelected -> {
                _state.update {
                    it.copy(
                        graphDisplayed = true,
                        chartLabel = event.exerciseName
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    getExercisesWithName(event.exerciseName)
                }
            }

            AnalysisScreenEvent.OnGeneralSelected -> {
                _state.update {
                    it.copy(
                        graphDisplayed = true,
                        chartLabel = MetricsMode.GENERAL.name
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    getGeneralTrainings()
                }
            }

            is AnalysisScreenEvent.OnTrainingIdSelected -> {
                _state.update {
                    it.copy(
                        graphDisplayed = true,
                        chartLabel = event.name
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    getParticularTrainings(trainingId = event.trainingId)
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
                    if (state.value.metricsMode == MetricsMode.EXERCISE) {
                        prepareMetricsDataBasedOnAnalysisModeExercise(
                            exercises = _state.value.historyExercises,
                            analysisMode = analysisMode
                        )
                    } else
                        prepareMetricsDataBasedOnAnalysisModeTraining(
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

    private fun prepareMetricsDataBasedOnAnalysisModeTraining(
        analysisMode: AnalysisMode,
        trainings: List<Training>? = null
    ) {
        when (analysisMode) {
            AnalysisMode.WEIGHT -> {
                prepareWeightDataTrainings(
                    historyTrainings = trainings
                )
            }

            AnalysisMode.LENGTH -> {
                prepareLengthData(
                    historyTrainings = trainings
                )
            }

            AnalysisMode.PROGRESS -> {
                prepareProgressDataTrainings(
                    historyTrainings = trainings
                )
            }
        }
    }

    private fun prepareMetricsDataBasedOnAnalysisModeExercise(
        analysisMode: AnalysisMode,
        exercises: List<Exercise>? = null,
    ) {
        when (analysisMode) {
            AnalysisMode.WEIGHT -> {
                exercises?.let {
                    prepareWeightDataExercises(
                        historyExercises = it
                    )
                }
            }

            AnalysisMode.LENGTH -> {}

            AnalysisMode.PROGRESS -> {
                prepareProgressDataExercises(
                    historyExercises = exercises
                )
            }
        }
    }

    private fun prepareProgressDataTrainings(
        historyTrainings: List<Training>? = null
    ) {
        historyTrainings?.let { trainings ->
            val weightList = trainings.map { it.totalWeightLifted }
            val weightBaseline = weightList.sum().div(weightList.size)
            val weightFivePercent = weightBaseline.div(20)

            val durationsList = trainings.map { Utils.trainingLengthToMin(it) }
            val durationsBaseline = durationsList.sum().div(durationsList.size)
            val durationFivePercent = durationsBaseline.div(20)

            val data = trainings.map { training ->
                val weightScore =
                    (training.totalWeightLifted - weightBaseline) / weightFivePercent


                val durationScore =
                    -((Utils.trainingLengthToMin(training) - durationsBaseline) / durationFivePercent)

                weightScore + durationScore
            }

            // Find the minimum value in the data list
            val minValue = data.minOrNull() ?: 0.0

            // Shift all values up so that the minimum value becomes zero
            val adjustedData = data.map { it - minValue }
            _state.update {
                it.copy(
                    metricsData = adjustedData,
                    metricsXAxisData = trainings.map { tr -> tr.name }
                )
            }
        }
    }

    private fun prepareProgressDataExercises(
        historyExercises: List<Exercise>?,
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

            _state.update {
                it.copy(
                    metricsData = adjustedData,
                    metricsXAxisData = exercises.map { tr -> tr.name }
                )
            }
        }
    }


    private fun prepareLengthData(
        historyTrainings: List<Training>? = null
    ) {
        val trainings = historyTrainings?.filter { it.endTime != 0L }
        val data = trainings?.map { Utils.trainingLengthToMin(it) }
        val xAxisData = trainings?.map { it.name }

        _state.update {
            it.copy(
                metricsData = data,
                metricsXAxisData = xAxisData
            )
        }
    }

    private fun prepareWeightDataTrainings(
        historyTrainings: List<Training>?
    ) {
        val data = historyTrainings?.map { it.totalWeightLifted }
        val xAxisData = historyTrainings?.map { it.name }

        _state.update {
            it.copy(
                metricsData = data,
                metricsXAxisData = xAxisData
            )
        }
    }

    private fun prepareWeightDataExercises(
        historyExercises: List<Exercise>
    ) {
        val data =
            historyExercises.filter { it.totalLiftedWeight != 0.0 }.map { it.totalLiftedWeight }

        val xAxisData =
            historyExercises.filter { it.totalLiftedWeight != 0.0 }.map { it.name }

        _state.update {
            it.copy(
                metricsData = data,
                metricsXAxisData = xAxisData
            )
        }
    }

    private suspend fun getGeneralTrainings() = viewModelScope.launch(Dispatchers.IO) {
        val trainings = trainingUseCaseProvider.getLatestHistoryTrainingsUseCase().invoke().first()
        _state.update {
            it.copy(historyTrainings = trainings)
        }
        prepareMetricsDataBasedOnAnalysisModeTraining(
            state.value.analysisMode,
            trainings = trainings
        )
    }

    private suspend fun getExercisesWithName(exerciseName: String) {
        val exercises =
            exerciseUseCaseProvider.getHistoryExercisesWithNameUseCase().invoke(exerciseName)
                .first().reversed()
        prepareMetricsDataBasedOnAnalysisModeExercise(
            state.value.analysisMode,
            exercises = exercises
        )
        _state.update {
            it.copy(historyExercises = exercises)
        }
    }

    private suspend fun getParticularTrainings(trainingId: Long) {
        val trainings =
            trainingUseCaseProvider.getParticularHistoryTrainingsUseCase()
                .invoke(trainingTemplateId = trainingId)
                .first()

        _state.update {
            it.copy(historyTrainings = trainings)
        }

        prepareMetricsDataBasedOnAnalysisModeTraining(
            state.value.analysisMode,
            trainings = trainings
        )
    }
}