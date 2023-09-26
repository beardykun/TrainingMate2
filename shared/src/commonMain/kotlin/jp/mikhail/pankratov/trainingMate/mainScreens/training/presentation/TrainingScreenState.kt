package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class TrainingScreenState(
    val greeting: String = "",
    val availableTrainings: List<Training>? = null,
    val selectedTraining: Training? = null,
    val showStartTrainingDialog: Boolean = false,
    val lastTraining: Training? = null,
    val ongoingTraining: Training? = null
)