package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class ThisTrainingState(val training: Training? = null, val exercises: List<Exercise>? = null)