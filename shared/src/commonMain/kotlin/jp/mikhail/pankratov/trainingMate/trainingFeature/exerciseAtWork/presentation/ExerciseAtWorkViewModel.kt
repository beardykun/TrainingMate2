package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import androidx.compose.ui.text.input.TextFieldValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.NotificationUtils
import jp.mikhail.pankratov.trainingMate.core.asResId
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.UseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError.InputErrorWeight
import jp.mikhail.pankratov.trainingMate.core.domain.util.Result
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state.ExerciseAtWorkState
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
    private val useCaseProvider: UseCaseProvider,
    private val trainingId: Long,
    private val exerciseTemplateId: Long,
    private val notificationUtils: NotificationUtils
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseAtWorkState())
    val state = combine(
        _state,
        useCaseProvider.getExerciseByTemplateIdUseCase()
            .invoke(exerciseTemplateId = exerciseTemplateId),
        useCaseProvider.getOngoingTrainingUseCase().invoke(),
        useCaseProvider.getExerciseFromHistoryUseCase().invoke(trainingId, exerciseTemplateId),
        useCaseProvider.getLatsSameExerciseUseCase().invoke(
            exerciseTemplateId = exerciseTemplateId,
            trainingId = trainingId
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
                    updateSets(sets, -minusWeight, -deleteItem.reps.toInt())
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
                if (state.value.uiState.isAutoInputEnabled) {
                    updateAutoInput()
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
                _state.update { currentState ->
                    currentState.copy(
                        exerciseDetails =
                        currentState.exerciseDetails.copy(setDifficulty = event.difficulty)
                    )
                }
            }

            is ExerciseAtWorkEvent.OnAutoInputToggled -> {
                _state.update {
                    it.copy(
                        uiState = it.uiState.copy(isAutoInputEnabled = event.checked)
                    )
                }
                if (event.checked) {
                    updateAutoInput()
                }
            }
        }
    }

    private fun updateAutoInput() {
        val exerciseLocal = state.value.exerciseDetails.exerciseLocal
        val sets = state.value.exerciseDetails.exercise?.sets ?: listOf()
        val pastSets = state.value.exerciseDetails.lastSameExercise?.sets ?: listOf()

        var counter = sets.size
        if (counter >= pastSets.size) {
            counter = sets.size - 1
        }
        val increment = when (pastSets[counter].difficulty) {
            SetDifficulty.Hard -> 0.0
            else -> if (exerciseLocal?.usesTwoDumbbells == true) {
                2.0
            } else
                2.5
        }
        val weight = pastSets[counter].weight.toDouble() + increment
        _state.update { currentState ->
            currentState.copy(
                exerciseDetails = currentState.exerciseDetails.copy(
                    weight = TextFieldValue(weight.toString()),
                    reps = TextFieldValue(pastSets[counter].reps),
                    errorReps = null,
                    errorWeight = null
                )
            )
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

        updateBestLiftedWeightIfNeeded()

        val weight =
            if (exerciseDetails.exerciseLocal?.usesTwoDumbbells == true)
                exerciseDetails.weight.text.toDouble() * 2
            else
                exerciseDetails.weight.text.toDouble()
        val reps = exerciseDetails.reps.text.toInt()
        updateSets(sets = sets, weight = weight * reps, reps = reps)
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

    private fun updateSets(sets: List<ExerciseSet>, weight: Double, reps: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            updateTrainingData(trainingId, weight, reps, sets)

            val exerciseDetails = state.value.exerciseDetails
            useCaseProvider.getUpdateExerciseDataUseCase().invoke(
                exerciseDetails = exerciseDetails,
                sets = sets,
                weight = weight,
                reps = reps,
                trainingId = trainingId,
                exerciseTemplateId = exerciseTemplateId
            )
        }

    private suspend fun updateTrainingData(
        trainingId: Long,
        weight: Double,
        reps: Int,
        sets: List<ExerciseSet>
    ) {
        val exerciseDetails = state.value.exerciseDetails
        state.value.ongoingTraining?.let {
            useCaseProvider.getUpdateTrainingDataUseCase().invoke(
                exerciseDetails = exerciseDetails,
                ongoingTraining = it,
                reps = reps,
                sets = sets,
                trainingId = trainingId,
                weight = weight
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

        when (val weightError = validateWeight(exerciseDetails.weight.text)) {
            is Result.Error -> {
                _state.update {
                    it.copy(
                        exerciseDetails =
                        it.exerciseDetails.copy(errorWeight = weightError.error.asResId())
                    )
                }
                return true
            }

            is Result.Success -> {}
        }
        when (val repsError = validateReps(exerciseDetails.reps.text)) {
            is Result.Error -> {
                _state.update {
                    it.copy(
                        exerciseDetails =
                        it.exerciseDetails.copy(errorWeight = repsError.error.asResId())
                    )
                }
                return true
            }

            is Result.Success -> {}
        }
        return false
    }

    private fun updateBestLiftedWeightIfNeeded() =
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseDetails = state.value.exerciseDetails
            useCaseProvider.getUpdateBestLiftedWeightUseCase()
                .invoke(exerciseDetails = exerciseDetails)
        }

    private fun validateWeight(weight: String): Result<Unit, InputError> {
        try {
            weight.toDouble()
        } catch (e: NumberFormatException) {
            return Result.Error(InputErrorWeight.INVALID_FORMAT)
        }

        return if (weight == "0.0" || weight == "0") {
            Result.Error(InputErrorWeight.WEIGHT_CANT_BE_0)
        } else if (weight.contains(",")) {
            Result.Error(InputErrorWeight.USE_DOT_IN_WEIGHT)
        } else if (weight.isBlank()) {
            Result.Error(InputErrorWeight.EMPTY_WEIGHT)
        } else Result.Success(Unit)
    }

    private fun validateReps(reps: String): Result<Unit, InputError> {
        try {
            reps.toInt()
        } catch (e: NumberFormatException) {
            return Result.Error(InputError.InputErrorReps.INVALID_FORMAT)
        }
        return if (reps.isBlank()) {
            Result.Error(InputError.InputErrorReps.EMPTY_REPS)
        } else if (reps.contains(",") || reps.contains(".")) {
            Result.Error(InputError.InputErrorReps.REPS_IS_FLOAT)
        } else if (reps == "0") {
            Result.Error(InputError.InputErrorReps.REPS_CANT_BE_0)
        } else Result.Success(Unit)
    }
}