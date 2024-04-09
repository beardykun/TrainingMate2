package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.NotificationUtils
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state.ExerciseAtWorkState
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state.ExerciseDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

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
            exerciseDetails = state.exerciseDetails.copy(
                exercise = exercise,
                exerciseLocal = exerciseLocal,
                lastSameExercise = lastExercise
            ),
            ongoingTraining = ongoingTraining
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
                _state.update { currentState ->
                    currentState.copy(
                        timerState = currentState.timerState.copy(isExpanded = true)
                    )
                }
            }

            ExerciseAtWorkEvent.OnDropdownClosed -> {
                _state.update { currentState ->
                    currentState.copy(
                        timerState = currentState.timerState.copy(isExpanded = false)
                    )
                }
            }

            is ExerciseAtWorkEvent.OnDropdownItemSelected -> {
                timerJob?.cancel()
                _state.update { currentState ->
                    currentState.copy(
                        timerState = currentState.timerState.copy(
                            timerValue = event.item.toInt(),
                            timer = event.item.toInt(),
                            isExpanded = false
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnRepsChanged -> {
                _state.update {
                    it.copy(
                        exerciseDetails =
                        it.exerciseDetails.copy(
                            reps = event.newReps,
                            errorReps = null
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnWeightChanged -> {
                _state.update {
                    it.copy(
                        exerciseDetails =
                        it.exerciseDetails.copy(
                            weight = event.newWeight,
                            errorWeight = null
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnSetDelete -> {
                state.value.uiState.deleteItem?.let { deleteItem ->
                    val sets =
                        state.value.exerciseDetails.exercise?.sets?.minus(deleteItem) ?: emptyList()
                    val minusWeight = deleteItem.weight.toDouble() * deleteItem.reps.toInt()
                    updateSets(sets, -minusWeight)
                }
                _state.update {
                    it.copy(
                        uiState =
                        it.uiState.copy(
                            deleteItem = null,
                            isDeleteDialogVisible = false
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnDisplayDeleteDialog -> {
                _state.update {
                    it.copy(
                        uiState =
                        it.uiState.copy(
                            isDeleteDialogVisible = event.display,
                            deleteItem = event.item
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnAddNewSet -> {
                _state.update {
                    it.copy(
                        uiState =
                        it.uiState.copy(
                            isAnimating = true
                        )
                    )
                }
                handleAddSetEvent()
            }

            ExerciseAtWorkEvent.OnAnimationSeen -> {
                _state.update { currentState ->
                    currentState.copy(
                        uiState =
                        currentState.uiState.copy(
                            isAnimating = false
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnSetDifficultySelected -> {
                println("TAGGER ${event.difficulty}")

                val state = _state.updateAndGet { currentState ->
                    currentState.copy(
                        exerciseDetails =
                        currentState.exerciseDetails.copy(setDifficulty = event.difficulty)
                    )
                }
                println("TAGGER ${state.exerciseDetails.setDifficulty}")

            }
        }
    }

    private fun handleAddSetEvent() {
        if (invalidInput()) return

        val exerciseDetails = state.value.exerciseDetails
        val newInput = ExerciseSet(
            weight = exerciseDetails.weight.text,
            reps = exerciseDetails.reps.text,
            difficulty = exerciseDetails.setDifficulty
        )
        val sets = exerciseDetails.exercise?.sets?.plus(newInput) ?: emptyList()

        updateBestLiftedWeightIfNeeded(exerciseDetails.weight.text.toDouble())

        val weight =
            if (exerciseDetails.exerciseLocal?.usesTwoDumbbells == true)
                exerciseDetails.weight.text.toDouble() * 2
            else
                exerciseDetails.weight.text.toDouble()
        updateSets(sets, weight * exerciseDetails.reps.text.toInt())
        runTimerJob()
    }

    private fun runTimerJob() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            startTimer(state.value.timerState.timerValue).collect { counter ->
                _state.update {
                    it.copy(
                        timerState = it.timerState.copy(timer = counter),
                    )
                }
                if (counter == 0) {
                    _state.update {
                        it.copy(
                            timerState = it.timerState.copy(timer = state.value.timerState.timerValue),
                        )
                    }
                    notificationUtils.sendNotification()
                }
            }
        }
    }

    private fun updateSets(sets: List<ExerciseSet>, weight: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            updateTrainingTime(trainingId, weight, sets)

            val exerciseDetails = state.value.exerciseDetails
            val exerciseTotalLifted = (exerciseDetails.exercise?.totalLiftedWeight ?: 0.0) + weight
            exerciseHistoryDatasource.updateExerciseSets(
                sets = sets,
                totalLiftedWeight = exerciseTotalLifted,
                trainingHistoryId = trainingId,
                exerciseTemplateId = exerciseTemplateId,
                reps = exerciseDetails.reps.text.toInt()
            )
        }

    private suspend fun updateTrainingTime(
        trainingId: Long,
        weight: Double,
        sets: List<ExerciseSet>
    ) {

        val exerciseDetails = state.value.exerciseDetails

        val totalLiftedWeight = (state.value.ongoingTraining?.totalWeightLifted ?: 0.0) + weight
        val doneExercises =
            state.value.ongoingTraining?.doneExercises?.toMutableList() ?: mutableListOf()
        val exerciseName = exerciseDetails.exercise?.name ?: ""


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
                doneExercised = doneExercises,
                sets = if (weight < 0) -1 else 1,
                reps = exerciseDetails.reps.text.toInt()
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
        val exerciseDetails = state.value.exerciseDetails

        val weightError = validateWeight(exerciseDetails.weight.text)
        val repsError = validateReps(exerciseDetails.reps.text)
        if (weightError != null) {
            _state.update {
                it.copy(
                    exerciseDetails =
                    it.exerciseDetails.copy(errorWeight = weightError)
                )
            }
            return true
        }

        if (repsError != null) {
            _state.update {
                it.copy(
                    exerciseDetails =
                    it.exerciseDetails.copy(errorReps = repsError)
                )
            }
            return true
        }
        return false
    }

    private fun updateBestLiftedWeightIfNeeded(currentLiftedWeight: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseDetails = state.value.exerciseDetails

            if (currentLiftedWeight > (exerciseDetails.exerciseLocal?.bestLiftedWeight ?: 0.0)) {
                exerciseDataSource.updateBestLiftedWeightById(
                    id = exerciseDetails.exerciseLocal?.id ?: -1,
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