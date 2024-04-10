package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import kotlinx.coroutines.flow.Flow

interface ITrainingHistoryDataSource {
    fun getTrainingRecordById(id: Long): Flow<Training>
    fun getLatestHistoryTrainings(): Flow<List<Training>>
    suspend fun insertTrainingRecord(training: Training)
    fun getOngoingTraining(): Flow<Training?>
    fun countOngoingTraining(): Flow<Long>
    suspend fun updateTrainingData(
        startTime: Long,
        trainingId: Long,
        totalLiftedWeight: Double,
        doneExercised: List<String>,
        sets: Int,
        reps: Int
    )

    suspend fun updateStatus(trainingId: Long, status: String = "COMPLETED")
    fun getGroupTrainings(group: String): Flow<List<Training>>
    fun getParticularTrainings(trainingTemplateId: Long): Flow<List<Training>>
    fun getTrainingsWithExercise(exerciseName: String): Flow<List<Training>>
    suspend fun deleteTrainingRecord(trainingId: Long)
    fun getLastTraining(trainingTemplateId: Long): Flow<Training?>
}