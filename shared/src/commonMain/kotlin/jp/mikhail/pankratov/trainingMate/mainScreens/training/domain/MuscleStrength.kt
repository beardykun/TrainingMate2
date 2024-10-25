package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain

data class MuscleStrength(
    val chest: MuscleStrengthItem = MuscleStrengthItem("Chest", 1.5),     // Bench Press
    val lowerBack: MuscleStrengthItem = MuscleStrengthItem("Lower Back", 2.2), // Deadlift
    val upperBack: MuscleStrengthItem = MuscleStrengthItem("Upper Back", 0.5), // Weighted Pull-ups
    val biceps: MuscleStrengthItem = MuscleStrengthItem("Biceps", 0.6), // Bicep Curls
    val triceps: MuscleStrengthItem = MuscleStrengthItem("Triceps", .75), // Triceps Dips
    val legs: MuscleStrengthItem = MuscleStrengthItem("Legs", 2.2), // Squats
    val shoulders: MuscleStrengthItem = MuscleStrengthItem("Shoulders", 0.8), //Overhead Press
)

data class MuscleStrengthItem(val name: String, val value: Double)
