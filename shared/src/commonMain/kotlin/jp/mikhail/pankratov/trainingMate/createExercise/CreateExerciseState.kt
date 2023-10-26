package jp.mikhail.pankratov.trainingMate.createExercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class CreateExerciseState(
    val exerciseName: String = "",
    val exerciseGroup: String = "",
    val usesTwoDumbbell: Boolean = false,
    val ongoingTraining: Training? = null
)