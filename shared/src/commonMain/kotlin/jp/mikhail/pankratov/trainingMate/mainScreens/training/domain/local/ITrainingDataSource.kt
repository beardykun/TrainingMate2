package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import kotlinx.coroutines.flow.Flow

interface ITrainingDataSource {
    suspend fun insertTraining(training: Training)
    fun getTrainings(): Flow<List<Training>>
    suspend fun trainingTableEmpty(): Boolean
    fun getTrainingById(trainingId: Long): Flow<Training>
    suspend fun updateExercises(exercises: List<String>, id: Long)
}