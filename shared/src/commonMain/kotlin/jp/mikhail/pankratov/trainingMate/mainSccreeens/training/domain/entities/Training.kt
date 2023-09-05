package jp.mikhail.pankratov.trainingMate.mainSccreeens.training.domain.entities

import jp.mikhail.pankratov.trainingMate.previouseExerciseStat.domain.entities.PreviousExerciseStat

data class Training(
    val id: String, // Unique identifier for the training session
    val userId: String, // Foreign key referencing the user who performed the training
    val name: String, // Name of the training session, e.g., "Leg Day"
    val exercises: List<PreviousExerciseStat>, // List of exercises performed during the training
    val totalDuration: Long, // Total duration of the training session in seconds
    val totalWeightLifted: Double, // Sum of weights lifted in all exercises in kilograms
    val timestamp: Long // Unix timestamp indicating when the training session was conducted
)