package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise

sealed class AddExercisesEvent {
    data class OnSelectExercise(val exercise: Exercise) : AddExercisesEvent()
}
