package jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.toTraining
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingHistoryDataSource(trainingDatabase: TrainingDatabase) : ITrainingHistoryDataSource {

    private val query = trainingDatabase.trainingHistoryQueries
    override fun getTrainingRecordById(id: Long): Flow<Training> {
        return query.getTrainingRecordById(id).asFlow().map { trainingHistory ->
            trainingHistory.executeAsOne().toTraining()
        }
    }

    override suspend fun insertTrainingRecord(training: Training) {
        query.insertTrainingRecord(
            id = training.id,
            name = training.name,
            groups = training.groups,
            start_time = training.startTime ?: 0,
            end_time = training.endTime ?: 0,
            total_lifted_weight = training.totalWeightLifted,
            user_id = training.userId
        )
    }
}