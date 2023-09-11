package jp.mikhail.pankratov.trainingMate.mainSccreeens.training.domain.local

import database.TrainingTemplate
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

fun TrainingTemplate.toTraining(): Training {
    return Training(
        id = id,
        name = name,
        groups = groups,
        description = description,
        userId = ""
    )
}
