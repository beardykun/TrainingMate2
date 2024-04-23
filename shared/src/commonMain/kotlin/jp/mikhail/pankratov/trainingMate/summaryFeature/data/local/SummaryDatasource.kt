package jp.mikhail.pankratov.trainingMate.summaryFeature.data.local


import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.domain.util.DateUtils
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.toMonthlySummary
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.toWeeklySummary
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
            training_duration = monthlySummary.trainingDuration.toLong()
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
            training_duration = weeklySummary.trainingDuration.toLong()
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

    override fun getTwoLastWeeklySummary(): Flow<List<WeeklySummary?>> {
        return weeklyQuery.getTwoLastWeeklySummaries()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { summaries ->
                summaries.map {
                    it.toWeeklySummary()
                }
            }
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

    override fun getTwoLastMonthlySummary(): Flow<List<MonthlySummary?>> {
        return monthlyQuery.getTwoLastMonthlySummaries()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { summaries ->
                summaries.map {
                    it.toMonthlySummary()
                }
            }
    }

    override suspend fun updateSummaries(
        additionalDuration: Double,
        additionalWeight: Double,
        numExercises: Int,
        numSets: Int,
        numReps: Int
    ) {
        monthlyQuery.updateMonthlySummary(
            year = currentYear,
            month_number = currentMonthNumber,
            training_duration = additionalDuration.toLong(),
            training_duration_ = additionalDuration.toLong(),
            total_lifted_weight = additionalWeight,
            total_lifted_weight_ = additionalWeight,
            total_lifted_weight__ = additionalWeight,
            num_exercises = numExercises.toLong(),
            num_exercises_ = numExercises.toLong(),
            num_sets = numSets.toLong(),
            num_reps = numReps.toLong()
        )

        weeklyQuery.updateWeeklySummary(
            year = currentYear,
            week_number = currentWeekNumber,
            training_duration = additionalDuration.toLong(),
            training_duration_ = additionalDuration.toLong(),
            total_lifted_weight = additionalWeight,
            total_lifted_weight_ = additionalWeight,
            total_lifted_weight__ = additionalWeight,
            num_exercises = numExercises.toLong(),
            num_exercises_ = numExercises.toLong(),
            num_sets = numSets.toLong(),
            num_reps = numReps.toLong()
        )
    }
}