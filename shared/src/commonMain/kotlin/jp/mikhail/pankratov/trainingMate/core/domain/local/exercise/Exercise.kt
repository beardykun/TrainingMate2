package jp.mikhail.pankratov.trainingMate.core.domain.local.exercise

data class Exercise(
    val id: Long? = null, // Unique identifier for the exercise
    val name: String, // Name of the exercise, e.g., "Bench Press"
    val group: String,
    val sets: List<ExerciseSet> = emptyList(), // List of sets performed in the exercise
    val reps: Int = 0, // List of sets performed in the exercise
    val date: String = "Unknown",
    val trainingHistoryId: Long,
    val trainingTemplateId: Long,
    val exerciseTemplateId: Long,
    val totalLiftedWeight: Double = 0.0
)