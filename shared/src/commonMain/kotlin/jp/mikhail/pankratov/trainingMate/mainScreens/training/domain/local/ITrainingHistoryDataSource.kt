package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import kotlinx.coroutines.flow.Flow

interface ITrainingHistoryDataSource {
    fun getTrainingRecordById(id: Long): Flow<Training>
    fun getLatestHistoryTrainings(): Flow<List<Training>>
    suspend fun insertTrainingRecord(training: Training)
    fun getOngoingTraining(): Flow<Training?>
    fun countOngoingTraining(): Flow<Long>
    suspend fun updateEndTime(trainingId: Long, totalLiftedWeight: Double)
    suspend fun updateStartTime(trainingId: Long, totalLiftedWeight: Double)
    suspend fun updateStatus(trainingId: Long)
    fun getGroupTrainings(group: String) : Flow<List<Training>>
    fun getParticularTrainings(trainingTemplateId: Long) : Flow<List<Training>>
    fun getTrainingsWithExercise(exerciseName: String) : Flow<List<Training>>
}