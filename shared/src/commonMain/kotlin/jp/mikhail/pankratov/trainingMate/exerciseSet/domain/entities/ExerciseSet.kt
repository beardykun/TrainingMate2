package jp.mikhail.pankratov.trainingMate.exerciseSet.domain.entities

data class ExerciseSet(
    val setNumber: Int, // The order in which the set was performed
    val repetitions: Int, // Number of reps in the set
    val weight: Double // Weight lifted in kilograms in the set
)