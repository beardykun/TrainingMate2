package jp.mikhail.pankratov.trainingMate.exercise.presentation

sealed class ExerciseAtWorkEvent {
    data class OnTimerChanged(val newTime: Int) : ExerciseAtWorkEvent()
    data object OnTimerStart: ExerciseAtWorkEvent()
    data class OnAddNewSet(val setData: String) : ExerciseAtWorkEvent()
}
