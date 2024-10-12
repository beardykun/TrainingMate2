package jp.mikhail.pankratov.trainingMate.trainigSelection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class TrainingSelectionViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val summaryUseCaseProvider: SummaryUseCaseProvider
) : ViewModel() {
    private val _state = MutableStateFlow(value = TrainingSelectionState())
    val state = combine(
        _state,
        trainingUseCaseProvider.getLocalTrainingsUseCase().invoke(),
        trainingUseCaseProvider.getOngoingTrainingUseCase().invoke()
    ) { state, localTrainings, ongoingTraining ->
        if (state.availableTrainings != localTrainings)
            state.copy(
                ongoingTraining = ongoingTraining,
                availableTrainings = localTrainings
            ) else state
    }.onEach {
        sortTrainings(it.sortType, it.availableTrainings)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = TrainingSelectionState()
        )

    fun onEvent(event: TrainingSelectionEvent) {
        when (event) {
            is TrainingSelectionEvent.OnTrainingItemClick -> {
                _state.update {
                    it.copy(
                        showStartTrainingDialog = event.shouldShowDialog,
                        selectedTraining = event.training
                    )
                }
            }

            is TrainingSelectionEvent.OnStartNewTraining -> {
                state.value.selectedTraining?.let { localTraining ->
                    _state.update {
                        it.copy(
                            showStartTrainingDialog = false,
                        )
                    }
                    viewModelScope.launch {
                        finishLastTrainingWhenStartingNew()
                        startNewTraining(localTraining)
                        event.onSuccess.invoke()
                    }
                }
            }

            TrainingSelectionEvent.OnDeleteTemplateConfirmClick -> {
                state.value.trainingId?.let { deleteTemplateTraining(trainingId = it) }
                _state.update {
                    it.copy(
                        showDeleteTemplateDialog = false,
                        trainingId = null
                    )
                }
            }

            TrainingSelectionEvent.OnDeleteTemplateDenyClick -> {
                _state.update {
                    it.copy(
                        showDeleteTemplateDialog = false,
                        trainingId = null
                    )
                }
            }

            is TrainingSelectionEvent.OnTrainingTemplateDelete -> {
                _state.update {
                    it.copy(
                        showDeleteTemplateDialog = true,
                        trainingId = event.id
                    )
                }
            }

            TrainingSelectionEvent.OnStartNewTrainingDeny -> {
                _state.update {
                    it.copy(
                        showStartTrainingDialog = false,
                        trainingId = null
                    )
                }
            }

            is TrainingSelectionEvent.OnTrainingTypeChanged -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            sortType = event.trainingType
                        )
                    }
                }
            }
        }
    }

    private suspend fun sortTrainings(
        trainingType: String,
        availableTrainings: List<TrainingLocal>?
    ) = withContext(Dispatchers.Default) {
        val typedTrainings = availableTrainings?.filter { it.groups.contains(trainingType) }
        _state.update {
            it.copy(
                typedTrainings = typedTrainings
            )
        }
    }

    private fun deleteTemplateTraining(trainingId: Long) = viewModelScope.launch(Dispatchers.IO) {
        trainingUseCaseProvider.getDeleteTrainingTemplateUseCase().invoke(trainingId)
    }

    private suspend fun startNewTraining(training: TrainingLocal) = withContext(Dispatchers.IO) {
        trainingUseCaseProvider.getInsertTrainingHistoryRecordUseCase().invoke(
            Training(
                trainingTemplateId = training.id!!,
                name = training.name,
                groups = training.groups,
                description = training.description,
                startTime = Clock.System.now().toEpochMilliseconds(),
                userId = "1",
                exercises = training.exercises
            )
        )
        summaryUseCaseProvider.getInsetSummaryUseCase().invoke()
    }

    private suspend fun finishLastTrainingWhenStartingNew() = withContext(Dispatchers.IO) {
        state.value.ongoingTraining?.id?.let { ongoingTrainingId ->
            if (state.value.ongoingTraining?.totalLiftedWeight == 0.0) {
                trainingUseCaseProvider.getDeleteTrainingHistoryRecordUseCase()
                    .invoke(trainingId = ongoingTrainingId)
                return@let
            }
            trainingUseCaseProvider.getUpdateTrainingHistoryStatusUseCase()
                .invoke(trainingId = ongoingTrainingId)
        }
    }
}