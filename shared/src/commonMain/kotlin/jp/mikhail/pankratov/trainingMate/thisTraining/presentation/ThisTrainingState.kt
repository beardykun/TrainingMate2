package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise

data class ThisTrainingState(val exercises: List<Exercise>? = null)