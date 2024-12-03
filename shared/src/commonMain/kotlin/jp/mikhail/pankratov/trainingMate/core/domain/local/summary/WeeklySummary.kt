package jp.mikhail.pankratov.trainingMate.core.domain.local.summary

data class WeeklySummary(
    val id: Long? = null, // Unique identifier for the weekly summary entry
    val weekNumber: Long, // Week number
    val year: Long, // Year
    val trainingDuration: Int = 0, // Total duration spent on training activities during the week (in minutes)
    val totalLiftedWeight: Double = 0.0, // Sum of the weights lifted across all exercises during the week
    val numWorkouts: Int = 0, // Total number of training sessions completed during the week
    val numExercises: Int = 0, // Total number of unique exercises performed during the week
    val numSets: Int = 0,
    val numReps: Int = 0,
    val avgDurationPerWorkout: Double = 0.0, // Average duration of each training session during the week (in minutes)
    val avgLiftedWeightPerExercise: Double = 0.0, // Average weight lifted per exercise during the week
    val avgLiftedWeightPerWorkout: Double = 0.0,
    val totalRestTime: Long = 0L, // Total rest time during the week (in seconds)
    val totalScore: Long = 0,
    val averageTrainingScore: Long = 0,
    val bestTrainingScore: Long = 0,
    val minTrainingScore: Long = 100
)

