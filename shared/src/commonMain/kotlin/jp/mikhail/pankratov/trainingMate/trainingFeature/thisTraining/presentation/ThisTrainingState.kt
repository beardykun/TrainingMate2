package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.ExerciseListItem

data class ThisTrainingState(
    val ongoingTraining: Training? = null,
    val exerciseLocals: List<ExerciseListItem>? = null,
    val trainingTime: String = "",
    val lastTraining: Training? = null
)