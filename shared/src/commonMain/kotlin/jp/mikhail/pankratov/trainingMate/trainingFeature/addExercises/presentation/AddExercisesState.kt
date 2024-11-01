package jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

@Immutable
data class AddExercisesState(
    val training: Training? = null,
    val sortedExercises: List<ExerciseListItem>? = null,
    val selectedExercises: List<String> = emptyList(),
    val isDeleteDialogVisible: Boolean = false,
    val selectedForDelete: ExerciseLocal? = null,
)