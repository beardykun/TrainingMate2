package jp.mikhail.pankratov.trainingMate.core.domain.local.exercise

data class Exercise(
    val id: Long? = null, // Unique identifier for the exercise
    val name: String, // Name of the exercise, e.g., "Bench Press"
    val group: String,
    val sets: List<String> = emptyList(), // List of sets performed in the exercise
    val bestLiftedWeight: Double = 0.0, // The highest weight lifted in this exercise so far
    val trainingHistoryId: Long,
    val exerciseTemplateId: Long,
    val totalLiftedWeight: Double = 0.0,
)