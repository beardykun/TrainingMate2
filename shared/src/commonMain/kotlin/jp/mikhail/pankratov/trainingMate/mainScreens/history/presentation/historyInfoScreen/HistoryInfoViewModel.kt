package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class HistoryInfoViewModel(
    trainingHistoryDataSource: ITrainingHistoryDataSource,
    exerciseHistoryDatasource: IExerciseHistoryDatasource,
    trainingHistoryId: Long
) : ViewModel() {

        private val _state = MutableStateFlow(HistoryInfoState())
        val state = combine(exerciseHistoryDatasource.getExerciseFromHistory())
}