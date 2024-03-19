package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal

data class TrainingScreenState(
    val greeting: String = "",
    val availableTrainings: List<TrainingLocal>? = null,
    val selectedTraining: TrainingLocal? = null,
    val showStartTrainingDialog: Boolean = false,
    val ongoingTraining: Training? = null,
    val lastTrainings: List<Training>? = null,
    val monthlySummary: MonthlySummary? = null,
    val weeklySummary: WeeklySummary? = null,
    val showDeleteDialog: Boolean = false,
    val showDeleteTemplateDialog: Boolean = false,
    val trainingId: Long? = null
)