package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseAtWorkHistoryViewModel(
    private val exerciseHistoryDatasource: IExerciseHistoryDatasource,
    private val exerciseName: String
) :
    ViewModel() {

    private val _state = MutableStateFlow(ExerciseAtWorkHistoryState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = ExerciseAtWorkHistoryState()
    )

    fun onEvent(event: ExerciseAtWorkHistoryEvent) {
        when (event) {
            ExerciseAtWorkHistoryEvent.OnExerciseHistoryLoad -> {
                loadExerciseHistory(exerciseName)
            }
        }
    }

    private fun loadExerciseHistory(exerciseName: String) = viewModelScope.launch(Dispatchers.IO) {
        val exercisesHistory = exerciseHistoryDatasource.getExercisesWithName(exerciseName).first()
        _state.update {
            it.copy(
                historyList = exercisesHistory
            )
        }
    }
}