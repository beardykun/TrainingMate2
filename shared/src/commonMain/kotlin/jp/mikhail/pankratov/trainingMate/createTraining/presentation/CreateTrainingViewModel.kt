package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateTrainingViewModel(private val trainingDataSource: ITrainingDataSource) : ViewModel() {

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

            CreateTrainingEvent.OnAddNewTraining -> {
                addNewTraining()
            }
        }
    }

    private fun addNewTraining() = viewModelScope.launch(Dispatchers.IO) {
        val training = TrainingLocal(
            id = null,
            name = state.value.trainingName.text,
            groups = state.value.selectedGroups.listToString(),
            exercises = emptyList(),
            description = state.value.trainingDescription
        )
        trainingDataSource.insertTraining(training = training)
    }
}