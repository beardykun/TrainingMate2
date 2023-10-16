package jp.mikhail.pankratov.trainingMate.core.domain.local.training

data class TrainingLocal(
    val id: Long? = null, // Unique identifier for the training session
    val name: String, // Name of the training session, e.g., "Leg Day"
    val groups: String,
    val exercises: List<String> = emptyList(),
    val description: String = ""
)
