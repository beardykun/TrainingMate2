package jp.mikhail.pankratov.trainingMate.core.domain.local.exercise

data class Exercise(
    val id: Long? = null, // Unique identifier for the exercise
    val name: String, // Name of the exercise, e.g., "Bench Press"
    val group: String,
    val sets: List<String> = emptyList(), // List of sets performed in the exercise
    val trainingHistoryId: Long,
    val trainingTemplateId: Long,
    val exerciseTemplateId: Long,
    val totalLiftedWeight: Double = 0.0
)