package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseSettingsDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseScreenViewModel(
    exerciseSettingsDatasource: IExerciseSettingsDatasource,
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
                incrementWeightDefault = 2.5,
                incrementWeightThisTrainingOnly = 2.5,
                isStrengthDefining = false,
                intervalSeconds = 55
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