package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class AddExercisesState(
    val training: Training? = null,
    val availableExercises: List<Exercise>? = null,
    val selectedExercises: List<String> = emptyList()
)