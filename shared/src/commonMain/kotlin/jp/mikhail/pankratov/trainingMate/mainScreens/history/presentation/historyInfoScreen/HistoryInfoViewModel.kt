package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HistoryInfoViewModel(
    trainingHistoryDataSource: ITrainingHistoryDataSource,
    exerciseHistoryDatasource: IExerciseHistoryDatasource,
    trainingHistoryId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryInfoState())
    val state = combine(
        trainingHistoryDataSource.getTrainingRecordById(trainingHistoryId),
        exerciseHistoryDatasource.getExercisesForTrainingHistory(trainingHistoryId = trainingHistoryId),
        _state
    ) { training, exercises, state ->
        state.copy(
            training = training, exercises = exercises
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = HistoryInfoState()
    )
}