package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain

import org.jetbrains.compose.resources.StringResource

data class TrainingScore(
    val score: Int,
    val comment: StringResource,
)