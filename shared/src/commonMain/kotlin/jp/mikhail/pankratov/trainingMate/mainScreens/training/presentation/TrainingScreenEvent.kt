package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal

sealed class TrainingScreenEvent {
    data object OnStartNewTraining : TrainingScreenEvent()
    data object OnDeleteConfirmClick : TrainingScreenEvent()
    data object OnDeleteDenyClick : TrainingScreenEvent()
    data class OnTrainingItemClick(
        val shouldShowDialog: Boolean = false,
        val training: TrainingLocal? = null
    ) : TrainingScreenEvent()

    data class OnLastTrainingDelete(val trainingId: Long) : TrainingScreenEvent()
    data class OnTrainingTemplateDelete(val id: Long) : TrainingScreenEvent()
    data object OnDeleteTemplateConfirmClick : TrainingScreenEvent()
    data object OnDeleteTemplateDenyClick : TrainingScreenEvent()
}