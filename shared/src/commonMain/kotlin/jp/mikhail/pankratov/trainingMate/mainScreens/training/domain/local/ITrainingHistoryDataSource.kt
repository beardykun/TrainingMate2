package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import kotlinx.coroutines.flow.Flow

interface ITrainingHistoryDataSource {
    fun getHistoryTrainingRecordById(id: Long): Flow<Training>
    fun getLatestHistoryTrainings(limit: Long, offset: Long): Flow<List<Training>>
    fun getParticularMonthTraining(year: Long, monthNum: Long): Flow<List<Training>>
    fun getParticularWeekTraining(year: Long, weekNum: Long): Flow<List<Training>>
    suspend fun insertTrainingRecord(training: Training)
    fun getOngoingTraining(): Flow<Training?>
    fun countOngoingTraining(): Flow<Long>
    suspend fun updateTrainingData(
        startTime: Long,
        trainingId: Long,
        totalLiftedWeight: Double,
        doneExercised: List<String>,
        sets: Int,
        reps: Int,
        restTime: Long,
        lastDoneExercise: String?
    )

    suspend fun finishTrainingHistory(trainingId: Long, status: String = "COMPLETED", score: Int)
    fun getGroupTrainings(group: String): Flow<List<Training>>
    fun getParticularHistoryTrainings(trainingTemplateId: Long): Flow<List<Training>>
    fun getTrainingsWithExercise(exerciseName: String): Flow<List<Training>>
    suspend fun deleteTrainingHistoryRecord(trainingId: Long)
    fun getLastSameTrainings(trainingTemplateId: Long): Flow<List<Training>?>
    fun getLastTraining(): Flow<Training?>
    fun getSearchResults(query: String): Flow<List<Training>>
}