package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

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
        exercises = exercises.split(", "),
        description = description,
    )
}

fun TrainingHistory.toTraining(): Training {
    return Training(
        id = id,
        trainingTemplateId = training_template_id,
        name = name,
        groups = groups,
        exercises = exercises.stringToList(),
        doneExercises = done_exercises.stringToList(),
        userId = user_id,
        startTime = start_time,
        endTime = end_time,
        status = status,
        totalWeightLifted = total_lifted_weight
    )
}
