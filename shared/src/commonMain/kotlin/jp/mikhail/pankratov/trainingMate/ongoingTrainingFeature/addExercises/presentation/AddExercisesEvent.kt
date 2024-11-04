package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal


sealed class AddExercisesEvent {
    data object OnDeleteExercise : AddExercisesEvent()
    data class OnSelectExercise(val exerciseName: String) : AddExercisesEvent()
    data class OnAddNewExercises(val onSuccess: () -> Unit) : AddExercisesEvent()
    data class OnDisplayDeleteDialog(
        val isDeleteVisible: Boolean,
        val exercise: ExerciseLocal? = null
    ) : AddExercisesEvent()
}
