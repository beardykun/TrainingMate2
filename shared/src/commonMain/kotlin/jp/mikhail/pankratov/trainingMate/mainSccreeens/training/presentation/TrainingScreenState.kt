package jp.mikhail.pankratov.trainingMate.mainSccreeens.training.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class TrainingScreenState(
    val greeting: String = "Good day",
    val availableTrainingHistories: List<Training>? = null
)