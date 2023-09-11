package jp.mikhail.pankratov.trainingMate.core.domain.local.exercise

data class Exercise(
    val id: String? = null, // Unique identifier for the exercise
    val name: String, // Name of the exercise, e.g., "Bench Press"
    val group: String,
    val image: String,
    val sets: String = "", // List of sets performed in the exercise
    val bestLiftedWeight: Double = 0.0, // The highest weight lifted in this exercise so far
    val duration: Long = 0, // Time it took to complete all sets, in seconds
    val trainingHistoryId: String? = null,
    val exerciseTemplateId: Long? = null,
    val totalLiftedWeight: Double = 0.0,
)