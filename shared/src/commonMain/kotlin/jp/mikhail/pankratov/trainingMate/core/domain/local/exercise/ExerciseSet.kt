package jp.mikhail.pankratov.trainingMate.core.domain.local.exercise

data class ExerciseSet(
    val weight: String,
    val reps: String,
    val difficulty: SetDifficulty = SetDifficulty.Light
)
