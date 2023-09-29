package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddExercisesViewModel(
    private val trainingDataSource: ITrainingDataSource,
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    private val exerciseDatasource: IExerciseDatasource,
    private val trainingId: Long
) : ViewModel() {

    private val _training = MutableStateFlow<Training?>(null)
    private val _availableExercises = MutableStateFlow<List<ExerciseLocal>>(emptyList())
    private val _selectedExercises = MutableStateFlow<List<String>>(emptyList())

    val state = combine(
        _training,
        _availableExercises,
        _selectedExercises
    ) { training, availableExercises, selectedExercises ->
        AddExercisesState(
            availableExerciseLocals = availableExercises,
            selectedExercises = selectedExercises,
            training = training
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = AddExercisesState()
    )

    init {
        loadTrainingAndExercises()
    }

    private fun loadTrainingAndExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            trainingHistoryDataSource.getOngoingTraining().collect { training ->
                training?.let { trainingNotNull ->
                    _training.value = trainingNotNull

                    val exercises =
                        exerciseDatasource.getExercisesByGroups(trainingNotNull.groups).first()
                    _selectedExercises.value = trainingNotNull.exercises
                    _availableExercises.value = exercises
                }
            }
        }
    }

    fun onEvent(event: AddExercisesEvent) {
        when (event) {
            is AddExercisesEvent.OnSelectExercise -> {
                _selectedExercises.update {
                    val list = if (!state.value.selectedExercises.contains(event.exercise)) {
                        state.value.selectedExercises.plus(event.exercise)
                    } else {
                        state.value.selectedExercises.minus(event.exercise)
                    }
                    list
                }
            }

            is AddExercisesEvent.OnAddNewExercises -> {
                state.value.training?.let { oldTraining ->
                    updateTraining(oldTraining)
                    event.onSuccess.invoke()
                }
            }
        }
    }

    private fun updateTraining(oldTraining: Training) = viewModelScope.launch(Dispatchers.IO) {
        trainingHistoryDataSource.insertTrainingRecord(oldTraining.copy(exercises = _selectedExercises.value))
        trainingDataSource.updateExercises(
            _selectedExercises.value,
            oldTraining.trainingTemplateId
        )
    }
}