package jp.mikhail.pankratov.trainingMate.core.domain.local.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils

data class ExerciseSet(
    val id: String,
    val weight: String,
    val reps: String,
    val difficulty: SetDifficulty = SetDifficulty.Light,
    val updateTime: Long,
    val restSec: Long?,
    val restTimeText: String? = if (restSec == null) "" else Utils.formatTimeText(restSec)
)
