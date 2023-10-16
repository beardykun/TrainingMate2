package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HistoryInfoViewModel(
    exerciseHistoryDatasource: IExerciseHistoryDatasource,
    trainingHistoryId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryInfoState())
    val state = combine(
        exerciseHistoryDatasource.getExercisesForTrainingHistory(trainingHistoryId = trainingHistoryId),
        _state
    ) { exercises, state ->
        if (state.exercises != exercises) {
            state.copy(exercises = exercises)
        } else state

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = HistoryInfoState()
    )
}