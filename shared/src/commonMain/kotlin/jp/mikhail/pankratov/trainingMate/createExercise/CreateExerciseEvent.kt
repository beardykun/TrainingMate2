package jp.mikhail.pankratov.trainingMate.createExercise

sealed class CreateExerciseEvent {
    data class OnExerciseNameChanged(val newName: String) : CreateExerciseEvent()
    data class OnExerciseGroupChanged(val newGroup: String) : CreateExerciseEvent()
    data object OnExerciseUsesTwoDumbbells : CreateExerciseEvent()
    data object OnExerciseCreate : CreateExerciseEvent()
}