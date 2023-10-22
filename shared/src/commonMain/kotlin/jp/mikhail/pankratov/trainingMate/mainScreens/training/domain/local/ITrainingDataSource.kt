package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import kotlinx.coroutines.flow.Flow

interface ITrainingDataSource {
    suspend fun insertTraining(training: TrainingLocal)
    fun getTrainings(): Flow<List<TrainingLocal>>
    suspend fun trainingTableEmpty(): Boolean
    fun getTrainingById(trainingId: Long): Flow<TrainingLocal>
    suspend fun updateExercises(exercises: List<String>, id: Long)
    suspend fun isTrainingExists(name: String) : Boolean
}