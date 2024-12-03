package jp.mikhail.pankratov.trainingMate.summaryFeature.data.local


import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.domain.util.DateUtils
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SummaryDatasource(db: TrainingDatabase) : ISummaryDatasource {

    private val weeklyQuery = db.weeklySummaryQueries
    private val monthlyQuery = db.monthlySummaryQueries


    private val currentYear: Long
        get() = DateUtils.currentYear
    private val currentWeekNumber: Long
        get() = DateUtils.currentWeekNumber
    private val currentMonthNumber: Long
        get() = DateUtils.currentMonthNumber

    override suspend fun insetSummary() {
        val monthlySummary =
            MonthlySummary(id = null, monthNumber = currentMonthNumber, year = currentYear)
        monthlyQuery.insertMonthlySummary(
            id = monthlySummary.id,
            year = monthlySummary.year,
            month_number = monthlySummary.monthNumber,
            num_exercises = monthlySummary.numExercises.toLong(),
            num_workouts = monthlySummary.numWorkouts.toLong(),
            num_sets = monthlySummary.numSets.toLong(),
            num_reps = monthlySummary.numReps.toLong(),
            total_lifted_weight = monthlySummary.totalLiftedWeight,
            avg_lifted_weight_per_workout = monthlySummary.avgLiftedWeightPerWorkout,
            avg_duration_per_workout = monthlySummary.avgDurationPerWorkout,
            avg_lifted_weight_per_exercise = monthlySummary.avgLiftedWeightPerExercise,
            training_duration = monthlySummary.trainingDuration.toLong(),
            total_rest_time = monthlySummary.totalRestTime,
            total_training_score = monthlySummary.totalScore,
            average_training_score = monthlySummary.averageTrainingScore,
            best_training_score = monthlySummary.bestTrainingScore,
            min_training_score = monthlySummary.minTrainingScore
        )

        val weeklySummary =
            WeeklySummary(id = null, weekNumber = currentWeekNumber, year = currentYear)
        weeklyQuery.insertWeeklySummary(
            id = weeklySummary.id,
            week_number = weeklySummary.weekNumber,
            num_exercises = weeklySummary.numExercises.toLong(),
            num_workouts = weeklySummary.numWorkouts.toLong(),
            num_sets = weeklySummary.numSets.toLong(),
            num_reps = weeklySummary.numReps.toLong(),
            total_lifted_weight = weeklySummary.totalLiftedWeight,
            year = weeklySummary.year,
            avg_duration_per_workout = weeklySummary.avgDurationPerWorkout,
            avg_lifted_weight_per_workout = weeklySummary.avgLiftedWeightPerWorkout,
            avg_lifted_weight_per_exercise = weeklySummary.avgLiftedWeightPerExercise,
            training_duration = weeklySummary.trainingDuration.toLong(),
            total_rest_time = weeklySummary.totalRestTime,
            total_training_score = weeklySummary.totalScore,
            average_training_score = weeklySummary.averageTrainingScore,
            best_training_score = weeklySummary.bestTrainingScore,
            min_training_score = weeklySummary.minTrainingScore
        )
    }

    override fun getWeeklySummary(): Flow<WeeklySummary?> {
        return weeklyQuery.getWeeklySummary(
            year = currentYear,
            week_number = currentWeekNumber
        )
            .asFlow()
            .map {
                it.executeAsOneOrNull()?.toWeeklySummary()
            }.flowOn(Dispatchers.IO)
    }

    override fun getLastWeeklySummaries(limit: Long): Flow<List<WeeklySummary?>> {
        return weeklyQuery.getLastWeeklySummaries(limit = limit)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { summaries ->
                summaries.map {
                    it.toWeeklySummary()
                }
            }.flowOn(Dispatchers.IO)
    }

    override fun getMonthlySummary(): Flow<MonthlySummary?> {
        return monthlyQuery.getMonthlySummary(
            year = currentYear,
            month_number = currentMonthNumber
        )
            .asFlow()
            .map {
                it.executeAsOneOrNull()?.toMonthlySummary()
            }.flowOn(Dispatchers.IO)
    }

    override fun getLastMonthlySummaries(limit: Long): Flow<List<MonthlySummary?>> {
        return monthlyQuery.getLastMonthlySummaries(limit = limit)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { summaries ->
                summaries.map {
                    it.toMonthlySummary()
                }
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateSummaries(
        additionalDuration: Double,
        additionalWeight: Double,
        additionalExercises: Int,
        additionalSets: Int,
        additionalReps: Int,
        additionalRestTime: Long,
        additionalScore: Long
    ) {
        monthlyQuery.updateMonthlySummary(
            year = currentYear,
            month_number = currentMonthNumber,
            training_duration = additionalDuration.toLong(),
            additional_duration = additionalDuration.toLong(),
            additional_weight = additionalWeight,
            additional_exercises = additionalExercises.toLong(),
            additional_sets = additionalSets.toLong(),
            additional_reps = additionalReps.toLong(),
            additional_rest_time = additionalRestTime,
            additional_score = additionalScore
        )

        weeklyQuery.updateWeeklySummary(
            year = currentYear,
            week_number = currentWeekNumber,
            training_duration = additionalDuration.toLong(),
            additional_duration = additionalDuration.toLong(),
            additional_weight = additionalWeight,
            additional_exercises = additionalExercises.toLong(),
            additional_sets = additionalSets.toLong(),
            additional_reps = additionalReps.toLong(),
            additional_rest_time = additionalRestTime,
            additional_score = additionalScore
        )
    }
}