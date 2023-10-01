package jp.mikhail.pankratov.trainingMate.exercise.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseAtWorkViewModel(
    private val exerciseHistoryDatasource: IExerciseHistoryDatasource,
    private val trainingId: Long,
    private val exerciseTemplateId: Long
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseAtWorkState())
    val state = combine(
        _state,
        exerciseHistoryDatasource.getExerciseFromHistory(trainingId, exerciseTemplateId)
    ) { state, exercise ->

        if (state.exercise != exercise) {
            state.copy(
                exercise = exercise
            )
        } else state

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = ExerciseAtWorkState()
    )

    private var timerJob: Job? = null
    fun onEvent(event: ExerciseAtWorkEvent) {
        when (event) {
            is ExerciseAtWorkEvent.OnAddNewSet -> {
                if (invalidInput()) return
                viewModelScope.launch(Dispatchers.IO) {
                    exerciseHistoryDatasource.updateExerciseSets(
                        sets = state.value.exercise?.sets?.plus("${state.value.weight.text} x ${state.value.reps.text}")!!,
                        trainingHistoryId = trainingId,
                        exerciseTemplateId = exerciseTemplateId
                    )
                }
            }

            ExerciseAtWorkEvent.OnTimerStart -> {
                timerJob?.cancel()
                timerJob = viewModelScope.launch {
                    startTimer(state.value.timer).collect { counter ->
                        _state.update {
                            it.copy(
                                timer = counter
                            )
                        }
                    }
                }
            }

            ExerciseAtWorkEvent.OnDropdownOpen -> {
                _state.update {
                    it.copy(
                        isExpanded = true
                    )
                }
            }

            ExerciseAtWorkEvent.OnDropdownClosed -> {
                _state.update {
                    it.copy(
                        isExpanded = false
                    )
                }
            }

            is ExerciseAtWorkEvent.OnDropdownItemSelected -> {
                timerJob?.cancel()
                _state.update {
                    it.copy(
                        timer = event.item.toInt(),
                        isExpanded = false
                    )
                }
            }

            is ExerciseAtWorkEvent.OnRepsChanged -> {
                _state.update {
                    it.copy(
                        reps = event.newReps,
                        errorReps = null
                    )
                }
            }

            is ExerciseAtWorkEvent.OnWeightChanged -> {
                _state.update {
                    it.copy(
                        weight = event.newWeight,
                        errorWeight = null
                    )
                }
            }
        }
    }

    private fun startTimer(initValue: Int) = flow {
        var count = initValue
        while (count >= 0) {
            emit(count--)
            kotlinx.coroutines.delay(1000)
        }
    }

    private fun invalidInput(): Boolean {
        val weightError = validateWeight(state.value.weight.text)
        val repsError = validateReps(state.value.reps.text)
        if (weightError != null) {
            _state.update {
                it.copy(
                    errorWeight = weightError
                )
            }
            return true
        }

        if (repsError != null) {
            _state.update {
                it.copy(
                    errorReps = repsError
                )
            }
            return true
        }
        return false
    }

    private fun validateWeight(weight: String): String? {
        if (weight == "0.0" || weight == "0") {
            return "Weight can't be 0"
        } else if (weight.contains(",")) {
            return "Please use '.' instead of ',' for weight"
        } else if (weight.isBlank()) {
            return "Weight field should not be empty"
        }
        try {
            weight.toDouble()
        } catch (e: NumberFormatException) {
            return "Invalid input format"
        }

        return null
    }

    private fun validateReps(reps: String): String? {
        return if (reps.isBlank()) {
            "Reps field should not be empty"
        } else if (reps.contains(",") || reps.contains(".")) {
            "Reps should not be a floating point number"
        } else if (reps == "0") {
            "Reps should not be 0"
        } else null
    }
}