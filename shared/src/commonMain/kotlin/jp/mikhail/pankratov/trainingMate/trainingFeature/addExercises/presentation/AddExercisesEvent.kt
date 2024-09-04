package jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal


sealed class AddExercisesEvent {
    data object OnDeleteExercise : AddExercisesEvent()
    data class OnSelectExercise(val exercise: String) : AddExercisesEvent()
    data class OnAddNewExercises(val onSuccess: () -> Unit) : AddExercisesEvent()
    data class OnDisplayDeleteDialog(
        val isDeleteVisible: Boolean,
        val exercise: ExerciseLocal? = null
    ) : AddExercisesEvent()
    data class OnSelectionChanged(val selectedType: SelectionType) : AddExercisesEvent()
    data object OnInitData : AddExercisesEvent()
}
