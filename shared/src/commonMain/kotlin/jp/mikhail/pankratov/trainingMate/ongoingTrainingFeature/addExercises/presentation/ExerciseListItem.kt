package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal

sealed class ExerciseListItem {
    data class Header(val muscleGroup: String) : ExerciseListItem()
    data class ExerciseItem(val exercise: ExerciseLocal, val isOptionsReveled: Boolean = false) :
        ExerciseListItem()
}
