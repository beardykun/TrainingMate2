package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import jp.mikhail.pankratov.trainingMate.core.domain.ToastManager
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.di.UtilsProvider
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.TimerDataHolder
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.AutoInputMode
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateAutoInputUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.ValidateInputUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state.ExerciseAtWorkState
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state.ExerciseDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ZERO = 0

class ExerciseAtWorkViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider,
    private val updateAutoInputUseCase: UpdateAutoInputUseCase,
    private val validateInputUseCase: ValidateInputUseCase,
    private val trainingId: Long,
    private val exerciseTemplateId: Long,
    private val utilsProvider: UtilsProvider,
    val permissionsController: PermissionsController
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseAtWorkState())
    val state = combine(
        _state,
        exerciseUseCaseProvider.getExerciseByTemplateIdUseCase()
            .invoke(exerciseTemplateId = exerciseTemplateId),
        trainingUseCaseProvider.getOngoingTrainingUseCase().invoke(),
        exerciseUseCaseProvider.getExerciseFromHistoryUseCase()
            .invoke(trainingId, exerciseTemplateId),
        exerciseUseCaseProvider.getLatsSameExerciseUseCase().invoke(
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
                utilsProvider.getTimerServiceRep().stopService()
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
                            inputError = null
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
                            inputError = null
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
                val sets = handleAddSetEvent()

                val autoInputMode = state.value.uiState.autoInputSelected
                if (autoInputMode != AutoInputMode.NONE) {
                    updateAutoInput(sets = sets ?: listOf(), autoInputMode)
                }
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

            is ExerciseAtWorkEvent.OnAutoInputChanged -> {
                val currentAutoInputInput = state.value.uiState.autoInputSelected
                val selectedAutoInput = if (currentAutoInputInput == event.autoInputMode) {
                    AutoInputMode.NONE
                } else {
                    event.autoInputMode
                }
                _state.update {
                    it.copy(
                        uiState = it.uiState.copy(autoInputSelected = selectedAutoInput)
                    )
                }
                if (selectedAutoInput != AutoInputMode.NONE) {
                    updateAutoInput(
                        state.value.exerciseDetails.exercise?.sets ?: listOf(),
                        selectedAutoInput
                    )
                }
            }
        }
    }

    private suspend fun requestNotificationPermission() {
        try {
            permissionsController.providePermission(Permission.REMOTE_NOTIFICATION)
            // Permission has been granted successfully.
        } catch (deniedAlways: DeniedAlwaysException) {
            // Permission is always denied.
            ToastManager.showToast("To receive rest time notifications, please enable them in your settings.")

        } catch (denied: DeniedException) {
            ToastManager.showToast("Permission denied. Enable notifications to be alerted when rest time ends")
            // Permission was denied.
        }
    }

    private fun updateAutoInput(sets: List<ExerciseSet>, autoInputMode: AutoInputMode) {
        val exerciseLocal = state.value.exerciseDetails.exerciseLocal
        val pastSets = state.value.exerciseDetails.lastSameExercise?.sets ?: listOf()

        val autoInputResult = updateAutoInputUseCase(
            exerciseLocal = exerciseLocal,
            currentSets = sets,
            pastSets = pastSets,
            autoInputMode = autoInputMode
        )
        autoInputResult?.let { autoInput ->
            _state.update { currentState ->
                currentState.copy(
                    exerciseDetails = currentState.exerciseDetails.copy(
                        weight = autoInput.weight,
                        reps = autoInput.reps,
                        inputError = null
                    )
                )
            }
        }
    }

    private fun handleAddSetEvent(): List<ExerciseSet>? {
        if (invalidInput()) return null

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
        return sets
    }

    private fun updateSets(sets: List<ExerciseSet>, weight: Double, reps: Int) =
        viewModelScope.launch {
            val exerciseDetails = state.value.exerciseDetails

            state.value.ongoingTraining?.let { ongoingTraining ->
                withContext(Dispatchers.IO) {
                    updateTrainingData(
                        trainingId,
                        weight,
                        reps,
                        sets,
                        exerciseDetails,
                        ongoingTraining
                    )
                    exerciseUseCaseProvider.getUpdateHistoryExerciseDataUseCase().invoke(
                        exerciseDetails = exerciseDetails,
                        sets = sets,
                        weight = weight,
                        reps = reps,
                        trainingId = trainingId,
                        exerciseTemplateId = exerciseTemplateId
                    )
                }
            }
        }

    private suspend fun updateTrainingData(
        trainingId: Long,
        weight: Double,
        reps: Int,
        sets: List<ExerciseSet>,
        exerciseDetails: ExerciseDetails,
        ongoingTraining: Training
    ) {
        trainingUseCaseProvider.getUpdateTrainingHistoryDataUseCase().invoke(
            exerciseDetails = exerciseDetails,
            ongoingTraining = ongoingTraining,
            reps = reps,
            sets = sets,
            trainingId = trainingId,
            weight = weight
        )
    }

    private fun runTimerJob() {
        viewModelScope.launch {
            requestNotificationPermission()

            utilsProvider.getTimerServiceRep().startService(state.value.timerState.timerValue)
            TimerDataHolder.timerValue.collect { counter ->
                _state.update {
                    it.copy(
                        timerState = it.timerState.copy(timer = counter ?: 0)
                    )
                }
                if (counter == ZERO) {
                    _state.update {
                        it.copy(
                            timerState = it.timerState.copy(timer = state.value.timerState.timerValue),
                        )
                    }
                }
            }
        }
    }

    private fun invalidInput(): Boolean {
        val exerciseDetails = state.value.exerciseDetails
        val inputError = validateInputUseCase.invoke(exerciseDetails)
        inputError?.let { error ->
            _state.update {
                it.copy(
                    exerciseDetails =
                    state.value.exerciseDetails.copy(inputError = error)
                )
            }
            return true
        }
        return false
    }

    private fun updateBestLiftedWeightIfNeeded() =
        viewModelScope.launch {
            val exerciseDetails = state.value.exerciseDetails
            withContext(Dispatchers.IO) {
                exerciseUseCaseProvider.getUpdateBestLiftedWeightUseCase()
                    .invoke(exerciseDetails = exerciseDetails)
            }
        }
}