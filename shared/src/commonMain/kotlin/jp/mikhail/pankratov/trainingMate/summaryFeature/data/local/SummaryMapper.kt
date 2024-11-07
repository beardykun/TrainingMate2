package jp.mikhail.pankratov.trainingMate.summaryFeature.data.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary


fun database.MonthlySummary.toMonthlySummary(): MonthlySummary {
    return MonthlySummary(
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
        avgLiftedWeightPerExercise = avg_lifted_weight_per_exercise,
        avgLiftedWeightPerWorkout = avg_lifted_weight_per_workout,
        totalRestTime = total_rest_time
    )
}

fun database.WeeklySummary.toWeeklySummary(): WeeklySummary {
    return WeeklySummary(
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
        avgLiftedWeightPerExercise = avg_lifted_weight_per_exercise,
        avgLiftedWeightPerWorkout = avg_lifted_weight_per_workout,
        totalRestTime = total_rest_time

    )
}