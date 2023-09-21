package jp.mikhail.pankratov.trainingMate.addExercises.presentation


sealed class AddExercisesEvent {
    data class OnSelectExercise(val exercise: String) : AddExercisesEvent()
    data class OnAddNewExercises(val onSuccess:() -> Unit) : AddExercisesEvent()
}
