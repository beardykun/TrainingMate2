package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.DefaultSettings
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseTrainingSettings
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseSettingsDatasource
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
    private val exerciseSettingsDatasource: IExerciseSettingsDatasource,
    trainingTemplateId: Long,
    exerciseTemplateId: Long
) :
    ViewModel() {
    private val _state = MutableStateFlow(ExerciseSettingsState())
    val state = _state.onStart {
        getExerciseSettings(
            exerciseSettingsDatasource = exerciseSettingsDatasource,
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseSettingsState())

    fun onEvent(event: ExerciseSettingsEvent) {
        when (event) {
            is ExerciseSettingsEvent.OnDefaultIncrementWeightChanged -> {
                _state.update {
                    it.copy(
                        exerciseSettings = it.exerciseSettings?.copy(
                            defaultSettings = it.exerciseSettings.defaultSettings.copy(
                                incrementWeightDefault = event.newWeight.text.toDouble(),
                                updated = true
                            )
                        )
                    )
                }
            }

            is ExerciseSettingsEvent.OnIncrementWeightChanged -> {
                _state.update {
                    it.copy(
                        exerciseSettings = it.exerciseSettings?.copy(
                            exerciseTrainingSettings = it.exerciseSettings.exerciseTrainingSettings.copy(
                                incrementWeightThisTrainingOnly = event.newWeight.text.toDouble(),
                                updated = true
                            )
                        )
                    )
                }
            }

            ExerciseSettingsEvent.OnApplyChanges -> {
                viewModelScope.launch {
                    val exerciseSettings = state.value.exerciseSettings
                    updateExerciseSettings(exerciseSettings)
                }
            }
        }
    }

    private suspend fun updateExerciseSettings(exerciseSettings: ExerciseSettings?) =
        withContext(Dispatchers.IO) {
            if (exerciseSettings?.exerciseTrainingSettings?.updated == true) {
                exerciseSettingsDatasource.updateTrainingExerciseSettings(
                    trainingTemplateId = exerciseSettings.trainingTemplateId,
                    exerciseTemplateId = exerciseSettings.exerciseTemplateId,
                    weight = exerciseSettings.exerciseTrainingSettings.incrementWeightThisTrainingOnly,
                    intervalSeconds = exerciseSettings.exerciseTrainingSettings.intervalSeconds
                )
            }
            if (exerciseSettings?.defaultSettings?.updated == true) {
                exerciseSettingsDatasource.updateDefaultSettings(
                    exerciseTemplateId = exerciseSettings.exerciseTemplateId,
                    weight = exerciseSettings.defaultSettings.incrementWeightDefault,
                    intervalSeconds = exerciseSettings.defaultSettings.intervalSecondsDefault,
                    isStrengthDefining = exerciseSettings.defaultSettings.isStrengthDefining
                )
            }
        }

    private fun getExerciseSettings(
        exerciseSettingsDatasource: IExerciseSettingsDatasource,
        trainingTemplateId: Long,
        exerciseTemplateId: Long
    ) = viewModelScope.launch {
        var exerciseSettings = exerciseSettingsDatasource.getExerciseSettings(
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        ).first()
        if (exerciseSettings == null) {
            exerciseSettings = ExerciseSettings(
                trainingTemplateId = trainingTemplateId,
                exerciseTemplateId = exerciseTemplateId,
                defaultSettings = DefaultSettings(
                    incrementWeightDefault = 2.5,
                    isStrengthDefining = false,
                    intervalSecondsDefault = 55
                ),
                exerciseTrainingSettings = ExerciseTrainingSettings(
                    incrementWeightThisTrainingOnly = 2.5,
                    intervalSeconds = 55
                )
            )
            exerciseSettingsDatasource.insertExerciseSettings(
                exerciseSettings = exerciseSettings
            )
        }
        _state.update {
            it.copy(exerciseSettings = exerciseSettings)
        }
    }
}
