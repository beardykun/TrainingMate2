package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import kotlinx.coroutines.flow.Flow

interface ITrainingDataSource {
    suspend fun insertLocalTraining(training: TrainingLocal)
    fun getLocalTrainings(): Flow<List<TrainingLocal>>
    suspend fun isLocalTrainingTableEmpty(): Boolean
    fun getTrainingById(trainingId: Long): Flow<TrainingLocal>
    suspend fun updateExercises(exercises: List<String>, id: Long)
    suspend fun isLocalTrainingExists(name: String): Boolean
    suspend fun deleteTrainingTemplate(id: Long)
}