package jp.mikhail.pankratov.trainingMate.core.domain.local.training

import jp.mikhail.pankratov.trainingMate.core.domain.Constants

data class Training(
    val id: Long? = null, // Unique identifier for the training session
    val trainingTemplateId: Long = -1, // Unique identifier for the training session
    val userId: String, // Foreign key referencing the user who performed the training
    val name: String, // Name of the training session, e.g., "Leg Day"
    val groups: String,
    val exercises: List<String> = emptyList(),
    val doneExercises: List<String> = emptyList(),
    val totalSets: Int = 0,
    val totalReps: Int = 0,
    val description: String = "",
    val totalLiftedWeight: Double = 0.0, // Sum of weights lifted in all exercises in kilograms
    val startTime: Long? = null,
    val endTime: Long? = null,
    val weekNumber: Long = 0,
    val monthNumber: Long = 0,
    val year: Long = 0,
    val status: String = Constants.ONGOING_STATUS,
    val restTime: Long = 0,
    val score: Long = 0
)
