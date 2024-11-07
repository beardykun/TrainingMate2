package jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local

import database.TrainingHistory
import database.TrainingTemplate
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.stringToList

fun TrainingTemplate.toTrainingLocal(): TrainingLocal {
    return TrainingLocal(
        id = id,
        name = name,
        groups = groups,
        exercises = exercises.stringToList(),
        description = description,
    )
}

fun TrainingHistory.toTraining(): Training {
    return Training(
        id = id,
        trainingTemplateId = training_template_id,
        userId = user_id,
        name = name,
        groups = groups,
        exercises = exercises.stringToList(),
        doneExercises = done_exercises.stringToList(),
        totalSets = total_sets.toInt(),
        totalReps = total_reps.toInt(),
        startTime = start_time,
        endTime = end_time,
        totalLiftedWeight = total_lifted_weight,
        weekNumber = week_number ?: 0L,
        monthNumber = month_number ?: 0L,
        year = year ?: 0L,
        status = status,
        restTime = rest_time ?: 0L
    )
}