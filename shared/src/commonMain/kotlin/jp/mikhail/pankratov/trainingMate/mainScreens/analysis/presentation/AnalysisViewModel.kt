package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnalysisViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider
) : ViewModel() {

    private val _state: MutableStateFlow<AnalysisScreenSate> =
        MutableStateFlow(AnalysisScreenSate())
    val state =
        combine(
            _state,
            trainingUseCaseProvider.getLocalTrainingsUseCase().invoke(),
            exerciseUseCaseProvider.getAllLocalExercisesUseCase().invoke()
        ) { state, localTrainings, localExercises ->
            state.copy(
                localTrainings = localTrainings.filter { it.exercises.isNotEmpty() },
                localExercises = localExercises.filter { it.bestLiftedWeight != 0.0 }
            )
        }.onStart {
            onEvent(AnalysisScreenEvent.OnGeneralSelected)
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
                viewModelScope.launch {
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
                getGeneralTrainings()
            }

            is AnalysisScreenEvent.OnTrainingIdSelected -> {
                _state.update {
                    it.copy(
                        graphDisplayed = true,
                        chartLabel = event.name
                    )
                }
                viewModelScope.launch {
                    getParticularTrainings(trainingId = event.trainingId)
                }
            }

            is AnalysisScreenEvent.OnAnalysisModeChanged -> {
                val analysisMode = when (event.analysisMode) {
                    AnalysisMode.WEIGHT.name -> AnalysisMode.WEIGHT
                    AnalysisMode.LENGTH.name -> AnalysisMode.LENGTH
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
        }
    }

    private fun prepareLengthData(
        historyTrainings: List<Training>? = null
    ) {
        val trainings = historyTrainings?.filter { it.endTime != 0L }
        val data = trainings?.map { Utils.trainingLengthToMin(it) }
        val xAxisData =
            trainings?.map {
                if (state.value.metricsMode.name == MetricsMode.GENERAL.name) it.name else Utils.formatEpochMillisToDate(
                    it.startTime ?: 0
                )
            }

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
        val data = historyTrainings?.map { it.totalLiftedWeight }
        val xAxisData = historyTrainings?.map {
            if (state.value.metricsMode.name == MetricsMode.GENERAL.name) it.name else Utils.formatEpochMillisToDate(
                it.startTime ?: 0
            )
        }

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
            historyExercises.filter { it.totalLiftedWeight != 0.0 }.map { it.date }

        _state.update {
            it.copy(
                metricsData = data,
                metricsXAxisData = xAxisData
            )
        }
    }

    private fun getGeneralTrainings() = viewModelScope.launch {
        val trainings =
            trainingUseCaseProvider.getLatestHistoryTrainingsUseCase().invoke().first().reversed()
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