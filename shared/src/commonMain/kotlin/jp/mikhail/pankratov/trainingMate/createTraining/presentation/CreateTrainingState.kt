package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.DatabaseContract

data class CreateTrainingState(
    val trainingName: String = "",
    val trainingGroups: List<String> =
        listOf(
            DatabaseContract.BICEPS_GROUP,
            DatabaseContract.TRICEPS_GROUP,
            DatabaseContract.SHOULDERS_GROUP,
            DatabaseContract.BACK_GROUP,
            DatabaseContract.CHEST_GROUP,
            DatabaseContract.LEGS_GROUP,
            DatabaseContract.ABS_GROUP
        ),
    val selectedGroups: List<String> = emptyList()
)