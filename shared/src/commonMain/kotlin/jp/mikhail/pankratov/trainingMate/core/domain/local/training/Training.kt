package jp.mikhail.pankratov.trainingMate.core.domain.local.training

import jp.mikhail.pankratov.trainingMate.previouseExerciseStat.domain.entities.PreviousExerciseStat

data class Training(
    val id: Long? = null, // Unique identifier for the training session
    val userId: String, // Foreign key referencing the user who performed the training
    val name: String, // Name of the training session, e.g., "Leg Day"
    val groups: String,
    val description: String,
    val exercises: List<PreviousExerciseStat>? = null, // List of exercises performed during the training
    val totalDuration: Long = 0, // Total duration of the training session in seconds
    val totalWeightLifted: Double = 0.0, // Sum of weights lifted in all exercises in kilograms
    val timestamp: Long?= null // Unix timestamp indicating when the training session was conducted
)
