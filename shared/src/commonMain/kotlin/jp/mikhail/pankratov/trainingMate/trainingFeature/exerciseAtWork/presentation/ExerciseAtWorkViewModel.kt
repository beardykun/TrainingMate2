package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.NotificationUtils
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
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

private const val SET_DIVIDER = " x "

class ExerciseAtWorkViewModel(
    private val exerciseHistoryDatasource: IExerciseHistoryDatasource,
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    private val exerciseDataSource: IExerciseDatasource,
    private val trainingId: Long,
    private val exerciseTemplateId: Long,
    private val notificationUtils: NotificationUtils
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseAtWorkState())
    val state = combine(
        _state,
        exerciseDataSource.getExerciseById(exerciseTemplateId),
        trainingHistoryDataSource.getOngoingTraining(),
        exerciseHistoryDatasource.getExerciseFromHistory(trainingId, exerciseTemplateId),
        exerciseHistoryDatasource.getLatsSameExercise(
            exerciseTemplateId = exerciseTemplateId,
            trainingHistoryId = trainingId
        )
    ) { state, exerciseLocal, ongoingTraining, exercise, lastExercise ->
        state.copy(
            exercise = exercise,
            exerciseLocal = exerciseLocal,
            ongoingTraining = ongoingTraining,
            lastSameExercise = lastExercise
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = ExerciseAtWorkState()
    )

    private var timerJob: Job? = null
    fun onEvent(event: ExerciseAtWorkEvent) {
        when (event) {
            ExerciseAtWorkEvent.OnTimerStart -> {
                runTimerJob()
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
                    val setData = it.split(SET_DIVIDER)
                    val minusWeight = setData.first().toDouble() * setData.last().toInt()
                    updateSets(sets, -minusWeight)
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

            is ExerciseAtWorkEvent.OnAddNewSet -> {
                _state.update {
                    it.copy(
                        isAnimating = true
                    )
                }
                handleAddSetEvent()
            }

            ExerciseAtWorkEvent.OnAnimationSeen -> {
                _state.update {
                    it.copy(
                        isAnimating = false
                    )
                }
            }
        }
    }

    private fun handleAddSetEvent() {
        if (invalidInput()) return

        val newInput = "${state.value.weight.text}$SET_DIVIDER${state.value.reps.text}"
        val sets = state.value.exercise?.sets?.plus(newInput) ?: emptyList()

        updateBestLiftedWeightIfNeeded(state.value.weight.text.toDouble())

        val weight =
            if (state.value.exerciseLocal?.usesTwoDumbbells == true)
                state.value.weight.text.toDouble() * 2
            else
                state.value.weight.text.toDouble()
        updateSets(sets, weight * state.value.reps.text.toInt())
        runTimerJob()
    }

    private fun runTimerJob() {
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

    private fun updateSets(sets: List<String>, weight: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            updateTrainingTime(trainingId, weight, sets)

            val exerciseTotalLifted = (state.value.exercise?.totalLiftedWeight ?: 0.0) + weight
            exerciseHistoryDatasource.updateExerciseSets(
                sets = sets,
                totalLiftedWeight = exerciseTotalLifted,
                trainingHistoryId = trainingId,
                exerciseTemplateId = exerciseTemplateId,
            )
        }

    private suspend fun updateTrainingTime(trainingId: Long, weight: Double, sets: List<String>) {
        val totalLiftedWeight = (state.value.ongoingTraining?.totalWeightLifted ?: 0.0) + weight
        val doneExercises =
            state.value.ongoingTraining?.doneExercises?.toMutableList() ?: mutableListOf()
        val exerciseName = state.value.exercise?.name ?: ""


        if (!doneExercises.contains(exerciseName) && sets.isNotEmpty()) {
            doneExercises.add(exerciseName)
        } else if (doneExercises.contains(exerciseName) && sets.isEmpty()) {
            doneExercises.remove(exerciseName)
        }
        if (state.value.ongoingTraining?.startTime == 0L) {
            trainingHistoryDataSource.updateStartTime(
                trainingId = trainingId,
                totalLiftedWeight = totalLiftedWeight,
                doneExercised = doneExercises
            )
        } else {
            trainingHistoryDataSource.updateEndTime(
                trainingId = trainingId,
                totalLiftedWeight = totalLiftedWeight,
                doneExercised = doneExercises
            )
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

    private fun updateBestLiftedWeightIfNeeded(currentLiftedWeight: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            if (currentLiftedWeight > (state.value.exerciseLocal?.bestLiftedWeight ?: 0.0)) {
                exerciseDataSource.updateBestLiftedWeightById(
                    id = state.value.exerciseLocal?.id ?: -1,
                    newBestWeight = currentLiftedWeight
                )
            }
        }

    private fun validateWeight(weight: String): String? {
        try {
            weight.toDouble()
        } catch (e: NumberFormatException) {
            return WEIGHT_ERROR_1
        }

        if (weight == "0.0" || weight == "0") {
            return WEIGHT_ERROR_2
        } else if (weight.contains(",")) {
            return WEIGHT_ERROR_3
        } else if (weight.isBlank()) {
            return WEIGHT_ERROR_4
        }
        return null
    }

    private fun validateReps(reps: String): String? {
        try {
            reps.toInt()
        } catch (e: NumberFormatException) {
            return WEIGHT_ERROR_1
        }
        return if (reps.isBlank()) {
            REPS_ERROR_1
        } else if (reps.contains(",") || reps.contains(".")) {
            REPS_ERROR_2
        } else if (reps == "0") {
            REPS_ERROR_3
        } else null
    }
}