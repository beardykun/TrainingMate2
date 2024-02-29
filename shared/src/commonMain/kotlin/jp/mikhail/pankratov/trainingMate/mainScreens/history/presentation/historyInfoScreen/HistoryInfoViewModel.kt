package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
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

class HistoryInfoViewModel(
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    exerciseHistoryDatasource: IExerciseHistoryDatasource,
    private val trainingHistoryId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryInfoState())
    val state = combine(
        trainingHistoryDataSource.getTrainingRecordById(trainingHistoryId),
        trainingHistoryDataSource.getOngoingTraining(),
        exerciseHistoryDatasource.getExercisesForTrainingHistory(trainingHistoryId = trainingHistoryId),
        _state
    ) { training, ongoingTraining, exercises, state ->
        state.copy(
            training = training,
            ongoingTraining = ongoingTraining,
            exercises = exercises
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = HistoryInfoState()
    )

    fun onEvent(event: HistoryInfoEvent) {
        when (event) {
            is HistoryInfoEvent.OnContinueTraining -> {
                if (state.value.ongoingTraining == null)
                    updateTrainingStatus(event.onSuccess)
                else
                    event.onError.invoke()
            }

            HistoryInfoEvent.OnError -> {
                _state.update {
                    it.copy(
                        isError = true,
                    )
                }
            }

            HistoryInfoEvent.OnFinishDeny -> {
                _state.update {
                    it.copy(
                        isError = false,
                    )
                }
            }

            is HistoryInfoEvent.OnFinishOngoingAndContinue -> {
                _state.update {
                    it.copy(
                        isError = false
                    )
                }
                finishOngoingTraining { event.onSuccess.invoke() }
            }
        }
    }

    private fun updateTrainingStatus(onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            trainingHistoryDataSource.updateStatus(
                trainingId = trainingHistoryId,
                status = "ONGOING"
            )
            withContext(Dispatchers.Main) {
                onSuccess.invoke()
            }
        }

    private fun finishOngoingTraining(onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            state.value.ongoingTraining?.id?.let {
                trainingHistoryDataSource.updateStatus(
                    trainingId = it
                )
            }
            updateTrainingStatus { onSuccess.invoke() }
        }
}