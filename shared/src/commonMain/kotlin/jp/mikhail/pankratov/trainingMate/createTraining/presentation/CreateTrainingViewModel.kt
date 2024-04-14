package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.listToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateTrainingViewModel(private val trainingUseCaseProvider: TrainingUseCaseProvider) :
    ViewModel() {

    private val _state = MutableStateFlow(CreateTrainingState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = CreateTrainingState()
    )

    fun onEvent(event: CreateTrainingEvent) {
        when (event) {
            is CreateTrainingEvent.OnTrainingGroupsChanged -> {
                if (state.value.selectedGroups.contains(event.group)) {
                    _state.update {
                        it.copy(selectedGroups = state.value.selectedGroups.minus(event.group))
                    }
                } else {
                    _state.update {
                        it.copy(selectedGroups = state.value.selectedGroups.plus(event.group))
                    }
                }
            }

            is CreateTrainingEvent.OnTrainingNameChanged -> {
                _state.update {
                    it.copy(trainingName = event.name)
                }
            }

            is CreateTrainingEvent.OnAddNewTraining -> {
                if (validNameInput().not()) return

                viewModelScope.launch {
                    if (trainingUseCaseProvider.getIsLocalTrainingExistsUseCase()
                            .invoke(state.value.trainingName.text)
                    ) {
                        _state.update {
                            it.copy(invalidNameInput = true)
                        }
                    } else {
                        _state.update {
                            it.copy(invalidNameInput = false)
                        }
                        addNewTraining()
                        event.onSuccess.invoke()
                    }
                }
            }
        }
    }

    private fun validNameInput(): Boolean {
        val trainingName = state.value.trainingName.text
        return if (trainingName.isBlank() || trainingName.length < 2) {
            _state.update {
                it.copy(invalidNameInput = true)
            }
            false
        } else true
    }

    private fun addNewTraining() = viewModelScope.launch(Dispatchers.IO) {
        val training = TrainingLocal(
            id = null,
            name = state.value.trainingName.text.uppercase(),
            groups = state.value.selectedGroups.listToString(),
            exercises = emptyList(),
            description = state.value.trainingDescription
        )
        trainingUseCaseProvider.getInsertLocalTrainingUseCase().invoke(trainingLocal = training)
    }
}