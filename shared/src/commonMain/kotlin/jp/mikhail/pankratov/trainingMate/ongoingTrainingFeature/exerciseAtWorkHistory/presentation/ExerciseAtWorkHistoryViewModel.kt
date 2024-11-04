package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseAtWorkHistoryViewModel(
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider,
    private val exerciseName: String
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
            else -> {}
        }
    }

    private fun loadExerciseHistory(exerciseName: String) = viewModelScope.launch {
        val exercisesHistory =
            exerciseUseCaseProvider.getHistoryExercisesWithNameUseCase().invoke(exerciseName)
                .first()
        _state.update {
            it.copy(
                historyList = exercisesHistory
            )
        }
    }
}