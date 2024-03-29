package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class HistoryInfoState(
    val training: Training? = null,
    val ongoingTraining: Training? = null,
    val exercises: List<Exercise>? = null,
    val isError: Boolean = false,
)
