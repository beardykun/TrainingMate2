package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CreateTrainingViewModel : ViewModel() {

    private val _state = MutableStateFlow(CreateTrainingState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = CreateTrainingState()
    )

    fun onEvent(event: CreateTrainingEvent) {
        when (event) {
            is CreateTrainingEvent.OnTrainingGroupsChanged -> {
                val newGroups = if (state.value.selectedGroups.contains(event.group)) {
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
        }
    }
}