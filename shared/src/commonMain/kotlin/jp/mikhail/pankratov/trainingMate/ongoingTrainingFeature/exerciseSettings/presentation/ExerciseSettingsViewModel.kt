package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.commomUseCases.ValidateNumericInputUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseSettingsUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseSettingsViewModel(
    private val exerciseSettingsUseCaseProvider: ExerciseSettingsUseCaseProvider,
    private val validateNumericInputUseCase: ValidateNumericInputUseCase,
    trainingTemplateId: Long,
    exerciseTemplateId: Long
) :
    ViewModel() {
    private val _state = MutableStateFlow(ExerciseSettingsState())
    val state = _state.onStart {
        val exerciseSettings = exerciseSettingsUseCaseProvider.getExerciseSettingsUseCase().invoke(
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        ).first()
        _state.update {
            it.copy(
                exerciseSettings = exerciseSettings,
                incrementWeightDefault = TextFieldValue(
                    exerciseSettings?.defaultSettings?.incrementWeightDefault?.toString() ?: "",
                    selection = TextRange(
                        (exerciseSettings?.defaultSettings?.incrementWeightDefault?.toString()
                            ?: "").length
                    )
                ),
                intervalSecondsDefault = TextFieldValue(
                    exerciseSettings?.defaultSettings?.intervalSecondsDefault?.toString() ?: ""
                ),
                incrementWeightThisTrainingOnly = TextFieldValue(
                    exerciseSettings?.exerciseTrainingSettings?.incrementWeightThisTrainingOnly?.toString()
                        ?: ""
                ),
                intervalSeconds = TextFieldValue(
                    exerciseSettings?.exerciseTrainingSettings?.intervalSeconds?.toString()
                        ?: ""
                )
            )
        }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseSettingsState())

    fun onEvent(event: ExerciseSettingsEvent) {
        when (event) {
            is ExerciseSettingsEvent.OnDefaultIncrementWeightChanged -> {
                _state.update {
                    it.copy(
                        incrementWeightDefault = event.newValue,
                        exerciseSettings = it.exerciseSettings?.copy(
                            defaultSettings = it.exerciseSettings.defaultSettings.copy(
                                updated = true
                            )
                        )
                    )
                }
            }

            is ExerciseSettingsEvent.OnIncrementWeightChanged -> {
                _state.update {
                    it.copy(
                        incrementWeightThisTrainingOnly = event.newValue,
                        exerciseSettings = it.exerciseSettings?.copy(
                            exerciseTrainingSettings = it.exerciseSettings.exerciseTrainingSettings.copy(
                                updated = true
                            )
                        )
                    )
                }
            }

            is ExerciseSettingsEvent.OnDefaultIntervalSecondsChanged -> {
                _state.update {
                    it.copy(
                        intervalSecondsDefault = event.newValue,
                        exerciseSettings = it.exerciseSettings?.copy(
                            defaultSettings = it.exerciseSettings.defaultSettings.copy(
                                updated = true
                            )
                        )
                    )
                }
            }

            is ExerciseSettingsEvent.OnIntervalSecondsChanged -> {
                _state.update {
                    it.copy(
                        intervalSeconds = event.newValue,
                        exerciseSettings = it.exerciseSettings?.copy(
                            exerciseTrainingSettings = it.exerciseSettings.exerciseTrainingSettings.copy(
                                updated = true
                            )
                        )
                    )
                }
            }

            is ExerciseSettingsEvent.OnApplyChanges -> {
                if (validateInput() != null) return

                val state = state.value
                val exerciseSettings = state.exerciseSettings
                val updatedExerciseSettings = exerciseSettings?.copy(
                    defaultSettings = exerciseSettings.defaultSettings.copy(
                        intervalSecondsDefault = state.intervalSecondsDefault.text.toLong(),
                        incrementWeightDefault = state.incrementWeightDefault.text.toDouble()
                    ),
                    exerciseTrainingSettings = exerciseSettings.exerciseTrainingSettings.copy(
                        intervalSeconds = state.intervalSeconds.text.toLong(),
                        incrementWeightThisTrainingOnly = state.incrementWeightThisTrainingOnly.text.toDouble()
                    )
                )

                viewModelScope.launch {
                    updateExerciseSettings(updatedExerciseSettings)
                    event.onSuccess.invoke()
                }
            }
        }
    }

    private fun validateInput(): InputError? {
        val state = state.value
        val incrementWeightDefaultError =
            validateNumericInputUseCase.validateFloat(state.incrementWeightDefault.text)
        val intervalSecondsDefaultError =
            validateNumericInputUseCase.validateInt(state.intervalSecondsDefault.text)
        val incrementWeightThisTrainingOnlyError =
            validateNumericInputUseCase.validateFloat(state.incrementWeightThisTrainingOnly.text)
        val intervalSecondsError =
            validateNumericInputUseCase.validateInt(state.intervalSeconds.text)

        _state.update {
            it.copy(
                inputErrorDefaultIncrementWeight = incrementWeightDefaultError,
                inputErrorDefaultIntervalSeconds = intervalSecondsDefaultError,
                inputErrorIncrementWeight = incrementWeightThisTrainingOnlyError,
                inputErrorIntervalSeconds = intervalSecondsError
            )
        }
        return incrementWeightDefaultError ?: incrementWeightThisTrainingOnlyError
        ?: intervalSecondsError ?: intervalSecondsDefaultError
    }

    private suspend fun updateExerciseSettings(exerciseSettings: ExerciseSettings?) =
        withContext(Dispatchers.IO) {
            if (exerciseSettings?.exerciseTrainingSettings?.updated == true) {
                exerciseSettingsUseCaseProvider.updateExerciseSettingsUseCase().invoke(
                    exerciseSettings = exerciseSettings
                )
            }
            if (exerciseSettings?.defaultSettings?.updated == true) {
                exerciseSettingsUseCaseProvider.updateDefaultExerciseSettingsUseCase().invoke(
                    exerciseSettings = exerciseSettings
                )
            }
        }
}
