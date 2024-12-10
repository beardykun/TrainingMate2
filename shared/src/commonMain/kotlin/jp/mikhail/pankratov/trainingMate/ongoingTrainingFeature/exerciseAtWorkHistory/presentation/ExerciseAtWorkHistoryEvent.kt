package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation

sealed class ExerciseAtWorkHistoryEvent {
    data class OnTabChanged(val tabName: ExerciseAtWorkHistoryTabs) : ExerciseAtWorkHistoryEvent()
}