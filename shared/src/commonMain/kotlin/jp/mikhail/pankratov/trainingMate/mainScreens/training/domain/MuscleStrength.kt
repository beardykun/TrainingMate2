package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain

data class MuscleStrength(
    val chest: Double = 1.5,     // Bench Press
    val lowerBack: Double = 2.2, // Deadlift
    val upperBack: Double = 1.0, // Weighted Pull-ups
    val biceps: Double = 0.6,    // Bicep Curls
    val triceps: Double = 0.75,   // Triceps Dips
    val legs: Double = 2.2,      // Squats
    val shoulders: Double = 0.8  // Overhead Press
)
