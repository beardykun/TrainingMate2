package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

sealed class TrainingScreenEvent {
    data object OnStartNewTraining : TrainingScreenEvent()
    data class OnTrainingItemClick(
        val shouldShowDialog: Boolean = false,
        val training: Training? = null
    ) : TrainingScreenEvent()
}