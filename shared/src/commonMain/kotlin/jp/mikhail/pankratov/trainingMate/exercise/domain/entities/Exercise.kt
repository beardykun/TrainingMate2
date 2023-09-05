package jp.mikhail.pankratov.trainingMate.exercise.domain.entities

import jp.mikhail.pankratov.trainingMate.previouseExerciseStat.domain.entities.PreviousExerciseStat

data class Exercise(
    val id: String, // Unique identifier for the exercise
    val name: String, // Name of the exercise, e.g., "Bench Press"
    val sets: List<PreviousExerciseStat>, // List of sets performed in the exercise
    val bestLiftedWeight: Double, // The highest weight lifted in this exercise so far
    val duration: Long, // Time it took to complete all sets, in seconds
    val previousStats: List<PreviousExerciseStat> // Stats for the last 3 times this exercise was performed
)