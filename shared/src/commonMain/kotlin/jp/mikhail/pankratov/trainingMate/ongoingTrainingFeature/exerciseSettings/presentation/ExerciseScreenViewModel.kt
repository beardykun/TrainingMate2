package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseSettingsDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ExerciseScreenViewModel(
    exerciseSettingsDatasource: IExerciseSettingsDatasource,
    trainingTemplateId: Long,
    exerciseTemplateId: Long
) :
    ViewModel() {
    private val _state = MutableStateFlow(ExerciseSettingsState())
    val state = _state.onStart {
        exerciseSettingsDatasource.getExerciseSettings(
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseSettingsState())

    fun onEvent(event: ExerciseSettingsEvent) {

    }
}