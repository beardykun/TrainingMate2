package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal

sealed class ThisTrainingEvent {
    data class OnExerciseClick(
        val exercise: ExerciseLocal,
        val navigateToExercise: (Long) -> Unit
    ) : ThisTrainingEvent()

    data object EndTraining : ThisTrainingEvent()

    data object OnInitData: ThisTrainingEvent()
}