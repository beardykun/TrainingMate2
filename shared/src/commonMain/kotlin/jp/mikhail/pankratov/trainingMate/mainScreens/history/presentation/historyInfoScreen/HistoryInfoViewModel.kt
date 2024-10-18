package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.Constants
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
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
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    exerciseUseCaseProvider: ExerciseUseCaseProvider,
    private val trainingHistoryId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryInfoState())
    val state = combine(
        trainingUseCaseProvider.getHistoryTrainingRecordByIdUseCase().invoke(trainingHistoryId),
        trainingUseCaseProvider.getOngoingTrainingUseCase().invoke(),
        exerciseUseCaseProvider.getGetExercisesForTrainingHistoryUseCase()
            .invoke(trainingHistoryId = trainingHistoryId),
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
            trainingUseCaseProvider.getUpdateTrainingHistoryStatusUseCase()(
                trainingId = trainingHistoryId,
                status = Constants.ONGOING_STATUS
            )
            withContext(Dispatchers.Main) {
                onSuccess.invoke()
            }
        }

    private fun finishOngoingTraining(onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            state.value.ongoingTraining?.id?.let {
                trainingUseCaseProvider.getUpdateTrainingHistoryStatusUseCase().invoke(
                    trainingId = it
                )
            }
            updateTrainingStatus { onSuccess.invoke() }
        }
}