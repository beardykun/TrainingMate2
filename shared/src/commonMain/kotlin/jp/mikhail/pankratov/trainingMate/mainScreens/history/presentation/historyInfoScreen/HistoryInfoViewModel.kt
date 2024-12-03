package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HistoryInfoViewModel(
    trainingUseCaseProvider: TrainingUseCaseProvider,
    exerciseUseCaseProvider: ExerciseUseCaseProvider,
    trainingHistoryId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryInfoState())
    val state = combine(
        trainingUseCaseProvider.getHistoryTrainingRecordByIdUseCase().invoke(trainingHistoryId),
        exerciseUseCaseProvider.getGetExercisesForTrainingHistoryUseCase()
            .invoke(trainingHistoryId = trainingHistoryId),
        _state
    ) { training, exercises, state ->
        state.copy(
            training = training,
            exercises = exercises
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = HistoryInfoState()
    )

    fun onEvent(event: HistoryInfoEvent) {

    }
}