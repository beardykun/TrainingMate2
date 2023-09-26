package jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import kotlinx.coroutines.flow.Flow

interface ITrainingHistoryDataSource {
    fun getTrainingRecordById(id: Long): Flow<Training>
    suspend fun insertTrainingRecord(training: Training)
    fun getOngoingTraining(): Flow<Training?>
    fun countOngoingTraining(): Flow<Long>
}