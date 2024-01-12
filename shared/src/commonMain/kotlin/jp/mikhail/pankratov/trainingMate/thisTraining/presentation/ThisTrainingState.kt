package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.addExercises.presentation.ExerciseListItem
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class ThisTrainingState(
    val ongoingTraining: Training? = null,
    val exerciseLocals: List<ExerciseListItem>? = null,
    val trainingTime: String = ""
)