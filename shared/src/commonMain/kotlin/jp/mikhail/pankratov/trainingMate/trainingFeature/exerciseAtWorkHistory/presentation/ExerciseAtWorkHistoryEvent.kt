package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation

sealed class ExerciseAtWorkHistoryEvent {
    data object OnExerciseHistoryLoad : ExerciseAtWorkHistoryEvent()
}