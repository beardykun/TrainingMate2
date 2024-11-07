package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.data.local

import database.ExerciseHistory
import database.ExerciseSettings
import database.ExerciseTemplate
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.stringToSetList

fun ExerciseTemplate.toExerciseLocal(): ExerciseLocal {
    return ExerciseLocal(
        id = id,
        name = name,
        image = image,
        bestLiftedWeight = best_lifted_weight,
        group = exercise_group,
        usesTwoDumbbells = uses_two_dumbbells.toInt() != 0,
        isStrengthDefining = is_strength_defining.toInt() != 0
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

fun ExerciseSettings.toExerciseSettings(): jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings {
    return jp.mikhail.pankratov.trainingMate.core.domain.local.exerciseSettings.ExerciseSettings(
        id = id,
        trainingTemplateId = training_template_id,
        exerciseTemplateId = exercise_template_id,
        incrementWeightDefault = increment_weight_default,
        incrementWeightThisTrainingOnly = increment_weight_this_training_only,
        isStrengthDefining = is_strength_defining.toInt() != 0,
        intervalSeconds = interval_seconds
    )
}