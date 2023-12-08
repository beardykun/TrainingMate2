package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
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

            is AnalysisScreenEvent.OnTrainingNameSelected -> {
                getExercisesForTraining(trainingName = event.trainingName)
                _state.update {
                    it.copy(graphDisplayed = true)
                }
            }
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

    private fun getExercisesForTraining(trainingName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            exerciseHistoryDatasource.getExercisesForTrainingWithName(trainingName = trainingName)
                .collect { exercises ->
                    _state.update {
                        it.copy(
                            historyExercises = exercises
                        )
                    }
                }
        }
}