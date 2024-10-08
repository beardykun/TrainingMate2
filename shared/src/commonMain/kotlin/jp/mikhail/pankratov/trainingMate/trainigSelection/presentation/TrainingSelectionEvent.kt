package jp.mikhail.pankratov.trainingMate.trainigSelection.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal

sealed class TrainingSelectionEvent {
    data object OnDeleteTemplateConfirmClick : TrainingSelectionEvent()
    data object OnDeleteTemplateDenyClick : TrainingSelectionEvent()
    data class OnTrainingItemClick(val shouldShowDialog: Boolean, val training: TrainingLocal) :
        TrainingSelectionEvent()
    data class OnTrainingTemplateDelete(val id: Long) : TrainingSelectionEvent()
    data class OnStartNewTraining(val onSuccess: () -> Unit) : TrainingSelectionEvent()
    data class OnTrainingTypeChanged(val trainingType: String) : TrainingSelectionEvent()
    data object OnStartNewTrainingDeny : TrainingSelectionEvent()
}