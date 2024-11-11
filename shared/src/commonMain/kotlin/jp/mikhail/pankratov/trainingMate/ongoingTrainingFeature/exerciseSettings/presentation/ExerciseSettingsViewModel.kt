package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseSettingsUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseSettingsViewModel(
    private val exerciseSettingsUseCaseProvider: ExerciseSettingsUseCaseProvider,
    trainingTemplateId: Long,
    exerciseTemplateId: Long
) :
    ViewModel() {
    private val _state = MutableStateFlow(ExerciseSettingsState())
    val state = combine(
        _state, exerciseSettingsUseCaseProvider.getExerciseSettingsUseCase().invoke(
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        )
    ) { state, exerciseSettings ->
        if (state.exerciseSettings == null) {
            println("TAGGER 111")
            state.copy(
                exerciseSettings = exerciseSettings,
                incrementWeightDefault = state.incrementWeightDefault.takeIf { it.text.isNotEmpty() }
                    ?: TextFieldValue(
                        exerciseSettings?.defaultSettings?.incrementWeightDefault?.toString() ?: "",
                        selection = TextRange((exerciseSettings?.defaultSettings?.incrementWeightDefault?.toString() ?: "").length)
                    ),
                intervalSecondsDefault = state.intervalSecondsDefault.takeIf { it.text.isNotEmpty() }
                    ?: TextFieldValue(exerciseSettings?.defaultSettings?.intervalSecondsDefault?.toString() ?: ""),
                incrementWeightThisTrainingOnly = state.incrementWeightThisTrainingOnly.takeIf { it.text.isNotEmpty() }
                    ?: TextFieldValue(exerciseSettings?.exerciseTrainingSettings?.incrementWeightThisTrainingOnly?.toString() ?: ""),
                intervalSeconds = state.intervalSeconds.takeIf { it.text.isNotEmpty() }
                    ?: TextFieldValue(exerciseSettings?.exerciseTrainingSettings?.intervalSeconds?.toString() ?: "")
            )
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseSettingsState())

    fun onEvent(event: ExerciseSettingsEvent) {
        when (event) {
            is ExerciseSettingsEvent.OnDefaultIncrementWeightChanged -> {
                _state.update {
                    it.copy(
                        incrementWeightDefault = event.newValue,
                        exerciseSettings = it.exerciseSettings?.copy(
                            defaultSettings = it.exerciseSettings.defaultSettings.copy(
                                incrementWeightDefault = event.newValue.text.toDouble(),
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
                                incrementWeightThisTrainingOnly = event.newValue.text.toDouble(),
                                updated = true
                            )
                        )
                    )
                }
            }

            is ExerciseSettingsEvent.OnApplyChanges -> {
                viewModelScope.launch {
                    val exerciseSettings = state.value.exerciseSettings
                    updateExerciseSettings(exerciseSettings)
                    event.onSuccess.invoke()
                }
            }

            is ExerciseSettingsEvent.OnDefaultIntervalSecondsChanged -> {
                _state.update {
                    it.copy(
                        intervalSecondsDefault = event.newValue,
                        exerciseSettings = it.exerciseSettings?.copy(
                            defaultSettings = it.exerciseSettings.defaultSettings.copy(
                                intervalSecondsDefault = event.newValue.text.toLong(),
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
                                intervalSeconds = event.newValue.text.toLong(),
                                updated = true
                            )
                        )
                    )
                }
            }
        }
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
