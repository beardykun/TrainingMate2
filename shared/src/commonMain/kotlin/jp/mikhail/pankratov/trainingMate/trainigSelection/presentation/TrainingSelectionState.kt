package jp.mikhail.pankratov.trainingMate.trainigSelection.presentation

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.Constants
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal

@Immutable
data class TrainingSelectionState(
    val availableTrainings: List<TrainingLocal>? = null,
    val typedTrainings: List<TrainingLocal>? = null,
    val ongoingTraining: Training? = null,
    val selectedTraining: TrainingLocal? = null,
    val trainingId: Long? = null,
    val showDeleteTemplateDialog: Boolean = false,
    val showStartTrainingDialog: Boolean = false,
    val sortType: String = Constants.GROUPS[0]
)