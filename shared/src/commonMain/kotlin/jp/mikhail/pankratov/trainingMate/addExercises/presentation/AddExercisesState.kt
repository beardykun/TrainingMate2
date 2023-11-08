package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class AddExercisesState(
    val training: Training? = null,
    val availableExerciseLocals: List<ExerciseLocal>? = null,
    val selectedExercises: List<String> = emptyList(),
    val isDeleteDialogVisible: Boolean = false,
    val selectedForDelete: ExerciseLocal? = null
)