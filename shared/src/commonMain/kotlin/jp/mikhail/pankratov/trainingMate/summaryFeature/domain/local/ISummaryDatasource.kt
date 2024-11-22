package jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import kotlinx.coroutines.flow.Flow

interface ISummaryDatasource {
    suspend fun insetSummary()
    fun getWeeklySummary(): Flow<WeeklySummary?>
    fun getLastWeeklySummaries(limit: Long): Flow<List<WeeklySummary?>>
    fun getMonthlySummary(): Flow<MonthlySummary?>
    fun getLastMonthlySummaries(limit: Long): Flow<List<MonthlySummary?>>
    suspend fun updateSummaries(
        additionalDuration: Double,
        additionalWeight: Double,
        numExercises: Int,
        numSets: Int,
        numReps: Int,
        totalRestTime: Long
    )
}