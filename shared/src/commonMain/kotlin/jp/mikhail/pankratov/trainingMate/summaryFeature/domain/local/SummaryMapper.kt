package jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local

import database.MonthlySummary
import database.WeeklySummary

fun MonthlySummary.toMonthlySummary(): jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary {
    return jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary(
        id = id,
        monthNumber = month_number,
        year = year,
        trainingDuration = training_duration.toInt(),
        totalLiftedWeight = total_lifted_weight,
        numWorkouts = num_workouts.toInt(),
        numExercises = num_exercises.toInt(),
        numSets = num_sets.toInt(),
        numReps = num_reps.toInt(),
        avgDurationPerWorkout = avg_duration_per_workout,
        avgLiftedWeightPerExercise = avg_lifted_weight_per_exercise
    )
}

fun WeeklySummary.toWeeklySummary(): jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary {
    return jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary(
        id = id,
        weekNumber = week_number,
        year = year,
        trainingDuration = training_duration.toInt(),
        totalLiftedWeight = total_lifted_weight,
        numWorkouts = num_workouts.toInt(),
        numExercises = num_exercises.toInt(),
        numSets = num_sets.toInt(),
        numReps = num_reps.toInt(),
        avgDurationPerWorkout = avg_duration_per_workout,
        avgLiftedWeightPerExercise = avg_lifted_weight_per_exercise
    )
}