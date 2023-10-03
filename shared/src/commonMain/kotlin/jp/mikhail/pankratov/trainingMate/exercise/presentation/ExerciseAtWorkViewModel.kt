package jp.mikhail.pankratov.trainingMate.exercise.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.NotificationUtils
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseAtWorkViewModel(
    private val exerciseHistoryDatasource: IExerciseHistoryDatasource,
    private val trainingId: Long,
    private val exerciseTemplateId: Long,
    private val notificationUtils: NotificationUtils
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

                val newInput = "${state.value.weight.text} x ${state.value.reps.text}"
                val sets = state.value.exercise?.sets?.plus(newInput) ?: emptyList()

                updateSets(sets)
            }

            ExerciseAtWorkEvent.OnTimerStart -> {
                timerJob?.cancel()
                timerJob = viewModelScope.launch {
                    startTimer(state.value.timerValue).collect { counter ->
                        _state.update {
                            it.copy(
                                timer = counter
                            )
                        }
                        if (counter == 0) {
                            _state.update {
                                it.copy(
                                    timer = state.value.timerValue
                                )
                            }
                            notificationUtils.sendNotification()
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
                        timerValue = event.item.toInt(),
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

            is ExerciseAtWorkEvent.OnSetDelete -> {
                state.value.deleteItem?.let {
                    val sets = state.value.exercise?.sets?.minus(it) ?: emptyList()

                    updateSets(sets)
                }
                _state.update {
                    it.copy(
                        deleteItem = null,
                        isDeleteDialogVisible = false
                    )
                }
            }

            is ExerciseAtWorkEvent.OnDisplayDeleteDialog -> {
                _state.update {
                    it.copy(
                        isDeleteDialogVisible = event.display,
                        deleteItem = event.item
                    )
                }
            }
        }
    }

    private fun updateSets(sets: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        exerciseHistoryDatasource.updateExerciseSets(
            sets = sets,
            trainingHistoryId = trainingId,
            exerciseTemplateId = exerciseTemplateId
        )
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
        try {
            weight.toDouble()
        } catch (e: NumberFormatException) {
            return "w1"
        }

        if (weight == "0.0" || weight == "0") {
            return "w2"
        } else if (weight.contains(",")) {
            return "w3"
        } else if (weight.isBlank()) {
            return "w4"
        }
        return null
    }

    private fun validateReps(reps: String): String? {
        return if (reps.isBlank()) {
            "r1"
        } else if (reps.contains(",") || reps.contains(".")) {
            "r2"
        } else if (reps == "0") {
            "r3"
        } else null
    }
}