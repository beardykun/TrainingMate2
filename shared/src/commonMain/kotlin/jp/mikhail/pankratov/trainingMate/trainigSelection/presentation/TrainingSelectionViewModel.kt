package jp.mikhail.pankratov.trainingMate.trainigSelection.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.SummaryUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class TrainingSelectionViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val summaryUseCaseProvider: SummaryUseCaseProvider
) : ViewModel() {
    private val _state = MutableStateFlow(value = TrainingSelectionState())
    val state = combine(
        _state,
        trainingUseCaseProvider.getLocalTrainingsUseCase().invoke()
    ) { state, localTrainings ->
        if (state.availableTrainings != localTrainings)
            state.copy(
                availableTrainings = localTrainings
            ) else state
    }.stateIn(
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
                    startNewTraining(localTraining)
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
        }
    }

    private fun deleteTemplateTraining(trainingId: Long) = viewModelScope.launch(Dispatchers.IO) {
        trainingUseCaseProvider.getDeleteTrainingTemplateUseCase().invoke(trainingId)
    }

    private fun startNewTraining(training: TrainingLocal) = viewModelScope.launch(Dispatchers.IO) {
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
}