package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import jp.mikhail.pankratov.trainingMate.core.domain.ToastManager
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.DefaultSettings
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseTrainingSettings
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseSettingsUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.Utils
import jp.mikhail.pankratov.trainingMate.core.randomUUID
import jp.mikhail.pankratov.trainingMate.di.UtilsProvider
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.TimerDataHolder
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases.AutoInputMode
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases.UpdateAutoInputUseCase
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases.ValidateInputUseCase
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state.ExerciseAtWorkState
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state.ExerciseDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

private const val ZERO = 0

class ExerciseAtWorkViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider,
    private val exerciseSettingsUseCaseProvider: ExerciseSettingsUseCaseProvider,
    private val updateAutoInputUseCase: UpdateAutoInputUseCase,
    private val validateInputUseCase: ValidateInputUseCase,
    private val viewModelArguments: ViewModelArguments,
    private val utilsProvider: UtilsProvider,
    val permissionsController: PermissionsController
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseAtWorkState())
    val state = combine(
        _state,
        exerciseUseCaseProvider.getExerciseByTemplateIdUseCase()
            .invoke(exerciseTemplateId = viewModelArguments.exerciseTemplateId),
        trainingUseCaseProvider.getOngoingTrainingUseCase().invoke(),
        exerciseUseCaseProvider.getExerciseFromHistoryUseCase()
            .invoke(viewModelArguments.trainingId, viewModelArguments.exerciseTemplateId),
        exerciseUseCaseProvider.getLatsSameExerciseUseCase().invoke(
            exerciseTemplateId = viewModelArguments.exerciseTemplateId,
            trainingId = viewModelArguments.trainingId,
            trainingTemplateId = viewModelArguments.trainingTemplateId
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
    }.onStart {
        getExerciseSettings(
            trainingTemplateId = viewModelArguments.trainingTemplateId,
            exerciseTemplateId = viewModelArguments.exerciseTemplateId
        )
    }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = ExerciseAtWorkState()
    )

    fun onEvent(event: ExerciseAtWorkEvent) {
        when (event) {
            ExerciseAtWorkEvent.OnTimerStart -> {
                runTimerJob()
            }

            ExerciseAtWorkEvent.OnTimerStop -> {
                utilsProvider.getTimerServiceRep().stopService()
                _state.update { currentState ->
                    currentState.copy(
                        timerState = currentState.timerState.copy(
                            timerValue = currentState.timerState.timerValue,
                            timerMin = state.value.timerState.timerValue / 60,
                            timerSec = state.value.timerState.timerValue % 60,
                            isCounting = false
                        )
                    )
                }
            }

            ExerciseAtWorkEvent.OnDropdownOpen -> {
                _state.update { currentState ->
                    currentState.copy(
                        timerState = currentState.timerState.copy(
                            isExpanded = true,
                            timerMin = state.value.timerState.timerValue / 60,
                            timerSec = state.value.timerState.timerValue % 60,
                        )
                    )
                }
                onEvent(ExerciseAtWorkEvent.OnTimerStop)
            }

            ExerciseAtWorkEvent.OnDropdownClosed -> {
                _state.update { currentState ->
                    currentState.copy(
                        timerState = currentState.timerState.copy(isExpanded = false)
                    )
                }
            }

            ExerciseAtWorkEvent.OnDropdownItemSelected -> {
                utilsProvider.getTimerServiceRep().stopService()
                _state.update { currentState ->
                    currentState.copy(
                        timerState = currentState.timerState.copy(
                            timerValue = state.value.timerState.timerMin * 60 + state.value.timerState.timerSec,
                            isExpanded = false,
                            isCounting = false
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnMinutesUpdated -> {
                _state.update {
                    it.copy(
                        timerState =
                        it.timerState.copy(
                            timerMin = event.newMinutes
                        )
                    )
                }
            }

            is ExerciseAtWorkEvent.OnSecondsUpdated -> {
                _state.update {
                    it.copy(
                        timerState =
                        it.timerState.copy(
                            timerSec = event.newSeconds
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
                handleDeleteSet()
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

    private fun handleDeleteSet() {
        state.value.uiState.deleteItem?.let { deleteItem ->
            val sets =
                state.value.exerciseDetails.exercise?.sets?.filter { it.id != deleteItem.id }
                    ?: emptyList()
            if (sets.size == state.value.exerciseDetails.exercise?.sets?.size) return
            val minusWeight = calculateSetTotalWeight(
                state.value.exerciseDetails.exerciseLocal?.usesTwoDumbbells,
                deleteItem.weight,
                deleteItem.reps
            )
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

    private suspend fun requestNotificationPermission() {
        try {
            permissionsController.providePermission(Permission.REMOTE_NOTIFICATION)
        } catch (deniedAlways: DeniedAlwaysException) {
            ToastManager.showToast(viewModelArguments.permissionRequest)

        } catch (denied: DeniedException) {
            ToastManager.showToast(viewModelArguments.permissionDenied)
        }
    }

    private fun updateAutoInput(sets: List<ExerciseSet>, autoInputMode: AutoInputMode) {
        val exerciseSettings = state.value.exerciseSettings
        val pastSets = state.value.exerciseDetails.lastSameExercise?.sets ?: listOf()

        val autoInputResult = exerciseSettings?.let {
            updateAutoInputUseCase(
                exerciseSettings = it,
                currentSets = sets,
                pastSets = pastSets,
                autoInputMode = autoInputMode
            )
        }
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
        val newSet = createNewSet(exerciseDetails)
        val sets = exerciseDetails.exercise?.sets?.plus(newSet) ?: emptyList()

        updateBestLiftedWeightIfNeeded()
        val reps = exerciseDetails.reps.text.toInt()

        val weight = calculateSetTotalWeight(
            exerciseDetails.exerciseLocal?.usesTwoDumbbells,
            exerciseDetails.weight.text,
            exerciseDetails.reps.text
        )
        updateSets(sets = sets, weight = weight, reps = reps)
        runTimerJob()
        return sets
    }

    private fun calculateSetTotalWeight(
        usesTwoDumbbells: Boolean?,
        weight: String,
        reps: String
    ): Double {
        val totalWeight =
            if (usesTwoDumbbells == true)
                weight.toDouble() * 2
            else
                weight.toDouble()
        return totalWeight * reps.toInt()
    }

    private fun createNewSet(exerciseDetails: ExerciseDetails): ExerciseSet {
        val now = Clock.System.now().toEpochMilliseconds()
        val lastSet = exerciseDetails.exercise?.sets?.lastOrNull()
        val restSec = if (lastSet?.updateTime == null) null else Utils.calculateRestSec(
            lastSet.updateTime, now
        )
        val newSet = ExerciseSet(
            id = randomUUID(),
            weight = exerciseDetails.weight.text,
            reps = exerciseDetails.reps.text,
            difficulty = exerciseDetails.setDifficulty,
            updateTime = now,
            restSec = restSec
        )
        return newSet
    }

    private fun updateSets(sets: List<ExerciseSet>, weight: Double, reps: Int) =
        viewModelScope.launch {
            val exerciseDetails = state.value.exerciseDetails

            state.value.ongoingTraining?.let { ongoingTraining ->
                withContext(Dispatchers.IO) {
                    updateTrainingData(
                        viewModelArguments.trainingId,
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
                        trainingId = viewModelArguments.trainingId,
                        exerciseTemplateId = viewModelArguments.exerciseTemplateId
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
            utilsProvider.getTimerServiceRep()
                .startService(state.value.timerState.timerValue)
            TimerDataHolder.timerValue.collect { counter ->
                _state.update {
                    it.copy(
                        timerState = it.timerState.copy(
                            timerMin = (counter ?: 0) / 60,
                            timerSec = (counter ?: 0) % 60,
                            isCounting = true
                        )
                    )
                }
                if (counter == ZERO) {
                    _state.update {
                        it.copy(
                            timerState = it.timerState.copy(
                                timerMin = state.value.timerState.timerValue / 60,
                                timerSec = state.value.timerState.timerValue % 60,
                                isCounting = false
                            ),
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

    override fun onCleared() {
        utilsProvider.getTimerServiceRep().stopService()
        super.onCleared()
    }

    private fun getExerciseSettings(
        trainingTemplateId: Long,
        exerciseTemplateId: Long
    ) = viewModelScope.launch {
        var exerciseSettings = exerciseSettingsUseCaseProvider.getExerciseSettingsUseCase().invoke(
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        ).first()
        if (exerciseSettings == null) {
            exerciseSettings = ExerciseSettings(
                trainingTemplateId = trainingTemplateId,
                exerciseTemplateId = exerciseTemplateId,
                defaultSettings = DefaultSettings(
                    incrementWeightDefault = 2.5,
                    intervalSecondsDefault = 55
                ),
                exerciseTrainingSettings = ExerciseTrainingSettings(
                    incrementWeightThisTrainingOnly = null,
                    intervalSeconds = null
                )
            )
            exerciseSettingsUseCaseProvider.insertExerciseSettingsUseCase().invoke(
                exerciseSettings = exerciseSettings
            )
        }
        _state.update {
            it.copy(exerciseSettings = exerciseSettings)
        }
    }
}