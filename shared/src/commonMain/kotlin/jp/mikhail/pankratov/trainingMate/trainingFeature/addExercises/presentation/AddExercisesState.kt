package jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class AddExercisesState(
    val training: Training? = null,
    val sortedExercises: List<ExerciseListItem>? = null,
    val selectedExercises: List<String> = emptyList(),
    val isDeleteDialogVisible: Boolean = false,
    val selectedForDelete: ExerciseLocal? = null,
)