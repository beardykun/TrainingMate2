package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local

import database.ExerciseHistory
import database.ExerciseTemplate
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.stringToList
import jp.mikhail.pankratov.trainingMate.core.stringToSetList

fun ExerciseTemplate.toExerciseLocal(): ExerciseLocal {
    return ExerciseLocal(
        id = id,
        name = name,
        image = image,
        bestLiftedWeight = best_lifted_weight,
        group = exercise_group,
        usesTwoDumbbells = uses_two_dumbbells.toInt() != 0
    )
}

fun ExerciseHistory.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        group = exercise_group,
        trainingHistoryId = training_history_id,
        trainingTemplateId = training_template_id,
        sets = sets.stringToSetList(),
        date = date,
        exerciseTemplateId = exercise_template_id,
        totalLiftedWeight = total_lifted_weight
    )
}