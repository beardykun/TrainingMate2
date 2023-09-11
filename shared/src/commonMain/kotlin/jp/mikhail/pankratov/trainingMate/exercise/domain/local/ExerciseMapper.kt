package jp.mikhail.pankratov.trainingMate.exercise.domain.local

import database.ExerciseTemplate
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise

fun ExerciseTemplate.toExercise(): Exercise {
    return Exercise(
        exerciseTemplateId = id,
        name = name,
        image = image,
        group = exercise_group
    )
}