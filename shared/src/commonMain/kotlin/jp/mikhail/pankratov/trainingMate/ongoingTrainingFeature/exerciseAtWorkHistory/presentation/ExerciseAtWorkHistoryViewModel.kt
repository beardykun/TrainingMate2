package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseAtWorkHistoryViewModel(
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider,
    private val exerciseName: String,
    private val trainingTemplateId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseAtWorkHistoryState())
    val state = _state
        .onStart { loadExerciseHistory(exerciseName) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = ExerciseAtWorkHistoryState()
        )

    fun onEvent(event: ExerciseAtWorkHistoryEvent) {
        when (event) {
            is ExerciseAtWorkHistoryEvent.OnTabChanged -> {
                when (event.tabName) {
                    ExerciseAtWorkHistoryTabs.ALL -> {
                        _state.update {
                            it.copy(historyExercisesToDisplay = state.value.historyExercises)
                        }
                    }

                    ExerciseAtWorkHistoryTabs.SAME_TRAINING -> getExercisesForTraining()
                }
            }
        }
    }

    private fun loadExerciseHistory(exerciseName: String) = viewModelScope.launch {
        val exercisesHistory =
            exerciseUseCaseProvider.getHistoryExercisesWithNameUseCase().invoke(exerciseName)
                .first()
        _state.update {
            it.copy(
                historyExercises = exercisesHistory,
                historyExercisesToDisplay = exercisesHistory
            )
        }
    }

    private fun getExercisesForTraining() = viewModelScope.launch {
        val exercisesHistory =
            withContext(Dispatchers.Default) { state.value.historyExercises?.filter { it.trainingTemplateId == trainingTemplateId } }
        _state.update {
            it.copy(historyExercisesToDisplay = exercisesHistory)
        }
    }
}