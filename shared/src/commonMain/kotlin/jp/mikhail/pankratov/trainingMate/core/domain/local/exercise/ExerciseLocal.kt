package jp.mikhail.pankratov.trainingMate.core.domain.local.exercise

data class ExerciseLocal(
    val id: Long? = null, // Unique identifier for the exercise
    val name: String, // Name of the exercise, e.g., "Bench Press"
    val bestLiftedWeight: Double = 0.0,
    val group: String,
    val image: String,
    val usesTwoDumbbells: Boolean = false,
    val isStrengthDefining: Boolean = false
)