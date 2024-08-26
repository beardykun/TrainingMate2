package jp.mikhail.pankratov.trainingMate.trainigSelection.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal

data class TrainingSelectionState(
    val availableTrainings: List<TrainingLocal>? = null,
    val typedTrainings: List<TrainingLocal>? = null,
    val ongoingTraining: Training? = null,
    val selectedTraining: TrainingLocal? = null,
    val trainingId: Long? = null,
    val showDeleteTemplateDialog: Boolean = false,
    val showStartTrainingDialog: Boolean = false
)