package jp.mikhail.pankratov.trainingMate.previouseExerciseStat.domain.entities

data class PreviousExerciseStat(
    val date: Long, // Unix timestamp for when the exercise was performed
    val bestLiftedWeight: Double, // Best lifted weight during that session
    val totalSets: Int, // Total number of sets during that session
    val duration: Long // Time it took to complete all sets in seconds
)