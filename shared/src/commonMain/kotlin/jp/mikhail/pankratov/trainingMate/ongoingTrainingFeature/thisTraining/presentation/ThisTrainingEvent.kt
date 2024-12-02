package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation.ExerciseListItem

sealed class ThisTrainingEvent {
    data class OnExerciseClick(
        val exercise: ExerciseLocal,
        val navigateToExercise: (Long) -> Unit
    ) : ThisTrainingEvent()

    data object EndTraining : ThisTrainingEvent()
    data object OnScoreTraining : ThisTrainingEvent()
    data class OnCollapsedEvent(val item: ExerciseListItem) : ThisTrainingEvent()
    data class OnExtendedEvent(val item: ExerciseListItem) : ThisTrainingEvent()
    data class OnRemoveExercise(val exerciseName: String) : ThisTrainingEvent()
}