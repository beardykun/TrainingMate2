package jp.mikhail.pankratov.trainingMate.exercise.domain.local

import database.ExerciseTemplate
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise

fun ExerciseTemplate.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        image = image,
        bestLiftedWeight = bestLiftedWeight,
        group = exercise_group
    )
}