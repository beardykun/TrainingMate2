package jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.toTraining
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class TrainingHistoryDataSource(db: TrainingDatabase) : ITrainingHistoryDataSource {

    private val query = db.trainingHistoryQueries
    private val exerciseQuery = db.exerciseHistoryQueries

    override fun getTrainingRecordById(id: Long): Flow<Training> {
        return query.getTrainingRecordById(id).asFlow().map { trainingHistory ->
            trainingHistory.executeAsOne().toTraining()
        }
    }

    override fun getLatestHistoryTrainings(): Flow<List<Training>> {
        return query.getLatestHistoryTrainings().asFlow().mapToList().map { trainings ->
            trainings.map {
                it.toTraining()
            }
        }
    }

    override suspend fun insertTrainingRecord(training: Training) {
        query.insertTrainingRecord(
            id = training.id,
            training_template_id = training.trainingTemplateId,
            name = training.name,
            groups = training.groups,
            exercises = training.exercises.listToString(),
            start_time = training.startTime ?: 0,
            end_time = training.endTime ?: 0,
            total_lifted_weight = training.totalWeightLifted,
            user_id = training.userId
        )
    }

    override fun getOngoingTraining(): Flow<Training?> {
        return query.getOngoingTraining().asFlow().map {
            it.executeAsOneOrNull()?.toTraining()
        }
    }

    override fun countOngoingTraining(): Flow<Long> {
        return query.countOngoingTraining().asFlow().map {
            it.executeAsOne()
        }
    }

    override suspend fun updateEndTime(trainingId: Long, totalLiftedWeight: Double) {
        query.updateEndTime(
            end_time = Clock.System.now().toEpochMilliseconds(),
            id = trainingId,
            total_lifted_weight = totalLiftedWeight
        )
    }

    override suspend fun updateStartTime(trainingId: Long, totalLiftedWeight: Double) {
        query.updateStartTime(
            start_time = Clock.System.now().toEpochMilliseconds(),
            id = trainingId,
            total_lifted_weight = totalLiftedWeight
        )
    }

    override suspend fun updateStatus(trainingId: Long, status: String) {
        query.updateStatus(
            status = status,
            id = trainingId
        )
    }

    override fun getGroupTrainings(group: String): Flow<List<Training>> {
        return query.getGroupTrainings(group).asFlow().mapToList().map { trainings ->
            trainings.map { training ->
                training.toTraining()
            }
        }
    }

    override fun getParticularTrainings(trainingTemplateId: Long): Flow<List<Training>> {
        return query.getParticularTrainings(trainingTemplateId).asFlow().mapToList()
            .map { trainings ->
                trainings.map { training ->
                    training.toTraining()
                }
            }
    }

    override fun getTrainingsWithExercise(exerciseName: String): Flow<List<Training>> {
        return query.getTrainingsWithExercise(exerciseName).asFlow().mapToList().map { trainings ->
            trainings.map { training ->
                training.toTraining()
            }
        }
    }

    override suspend fun deleteTrainingRecord(trainingId: Long) {
        query.deleteTrainingRecord(id = trainingId)
        exerciseQuery.deleteTrainingExercisesRecords(training_history_id = trainingId)
    }

    override fun getLastTraining(trainingTemplateId: Long): Flow<Training?> {
        return query.getLastTraining(training_template_id = trainingTemplateId).asFlow()
            .map { trainingHistory ->
                trainingHistory.executeAsOneOrNull()?.toTraining()
            }
    }
}