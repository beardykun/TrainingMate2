package jp.mikhail.pankratov.trainingMate.exerciseAtWorkHistory.presentation

sealed class ExerciseAtWorkHistoryEvent {
    data object OnExerciseHistoryLoad : ExerciseAtWorkHistoryEvent()
}