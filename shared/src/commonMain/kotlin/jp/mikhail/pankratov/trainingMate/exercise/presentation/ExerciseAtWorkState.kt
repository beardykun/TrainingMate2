package jp.mikhail.pankratov.trainingMate.exercise.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class ExerciseAtWorkState(
    val training: Training? = null,
    val exercise: Exercise? = null,
    val timer: Int = 60,
    val exerciseRecord: Exercise? = null
)