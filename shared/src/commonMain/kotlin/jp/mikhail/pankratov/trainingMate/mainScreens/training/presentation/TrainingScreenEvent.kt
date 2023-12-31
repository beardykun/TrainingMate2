package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal

sealed class TrainingScreenEvent {
    data object OnStartNewTraining : TrainingScreenEvent()
    data class OnTrainingItemClick(
        val shouldShowDialog: Boolean = false,
        val training: TrainingLocal? = null
    ) : TrainingScreenEvent()
}