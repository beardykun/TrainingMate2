package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import database.TrainingHistory
import database.TrainingTemplate
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.stringToList

fun TrainingTemplate.toTraining(): Training {
    return Training(
        id = id,
        name = name,
        groups = groups,
        exercises = exercises.split(", "),
        description = description,
        userId = ""
    )
}

fun TrainingHistory.toTraining(): Training {
    return Training(
        id = id,
        trainingTemplateId = training_template_id,
        name = name,
        groups = groups,
        exercises = exercises.stringToList(),
        userId = user_id,
        startTime = start_time,
        endTime = end_time,
        totalWeightLifted = total_lifted_weight
    )
}
