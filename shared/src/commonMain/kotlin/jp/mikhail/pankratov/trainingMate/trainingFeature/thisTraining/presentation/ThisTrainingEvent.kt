package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.ExerciseListItem

sealed class ThisTrainingEvent {
    data class OnExerciseClick(
        val exercise: ExerciseLocal,
        val navigateToExercise: (Long) -> Unit
    ) : ThisTrainingEvent()

    data object EndTraining : ThisTrainingEvent()
    data class OnCollapsedEvent(val item: ExerciseListItem) : ThisTrainingEvent()
    data class OnExtendedEvent(val item: ExerciseListItem) : ThisTrainingEvent()
}