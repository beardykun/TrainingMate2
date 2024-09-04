package jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation

import dev.icerock.moko.resources.StringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class AddExercisesState(
    val training: Training? = null,
    val availableExerciseLocals: List<ExerciseListItem>? = null,
    val sortedExercises: List<ExerciseListItem>? = null,
    val selectedExercises: List<String> = emptyList(),
    val isDeleteDialogVisible: Boolean = false,
    val selectedForDelete: ExerciseLocal? = null
)

enum class SelectionType(typeName: StringResource) {
    ADD(typeName = SharedRes.strings.add),
    REMOVE(typeName = SharedRes.strings.remove)
}