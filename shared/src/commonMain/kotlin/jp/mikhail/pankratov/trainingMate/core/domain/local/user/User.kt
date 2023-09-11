package jp.mikhail.pankratov.trainingMate.core.domain.local.user

data class User(
    val id: String,
    val username: String,
    val email:String,
    val age: Int,
    val weight: Double
)
