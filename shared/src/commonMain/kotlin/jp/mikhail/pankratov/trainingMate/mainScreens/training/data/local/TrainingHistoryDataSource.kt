package jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.util.DateUtils
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class TrainingHistoryDataSource(db: TrainingDatabase) : ITrainingHistoryDataSource {

    private val query = db.trainingHistoryQueries
    private val exerciseQuery = db.exerciseHistoryQueries

    override fun getHistoryTrainingRecordById(id: Long): Flow<Training> {
        return query.getTrainingRecordById(id).asFlow().map { trainingHistory ->
            trainingHistory.executeAsOne().toTraining()
        }.flowOn(Dispatchers.IO)
    }

    override fun getLatestHistoryTrainings(limit: Long, offset: Long): Flow<List<Training>> {
        return query.getLatestHistoryTrainings(limit = limit, offset = offset).asFlow()
            .mapToList(Dispatchers.IO)
            .map { trainings ->
                trainings.map {
                    it.toTraining()
                }
            }
    }

    override fun getParticularMonthTraining(year: Long, monthNum: Long): Flow<List<Training>> {
        return query.getParticularMonthTraining(year = year, month_number = monthNum)
            .asFlow().mapToList(Dispatchers.IO).map { trainings ->
                trainings.map { training ->
                    training.toTraining()
                }
            }
    }

    override fun getParticularWeekTraining(year: Long, weekNum: Long): Flow<List<Training>> {
        return query.getParticularWeekTraining(year = year, week_number = weekNum)
            .asFlow().mapToList(Dispatchers.IO).map { trainings ->
                trainings.map { training ->
                    training.toTraining()
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
            done_exercises = training.doneExercises.listToString(),
            total_sets = training.totalSets.toLong(),
            total_reps = training.totalReps.toLong(),
            start_time = training.startTime ?: 0,
            end_time = training.endTime ?: 0,
            total_lifted_weight = training.totalLiftedWeight,
            week_number = DateUtils.currentWeekNumber,
            month_number = DateUtils.currentMonthNumber,
            year = DateUtils.currentYear,
            user_id = training.userId,
            rest_time = training.restTime
        )
    }

    override fun getOngoingTraining(): Flow<Training?> {
        return query.getOngoingTraining().asFlow().map {
            it.executeAsOneOrNull()?.toTraining()
        }.flowOn(Dispatchers.IO)
    }

    override fun countOngoingTraining(): Flow<Long> {
        return query.countOngoingTraining().asFlow().map {
            it.executeAsOne()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateTrainingData(
        startTime: Long,
        trainingId: Long,
        totalLiftedWeight: Double,
        doneExercised: List<String>,
        sets: Int,
        reps: Int,
        restTime: Long
    ) {
        query.updateTrainingData(
            start_time = startTime,
            end_time = Clock.System.now().toEpochMilliseconds(),
            id = trainingId,
            total_lifted_weight = totalLiftedWeight,
            done_exercises = doneExercised.listToString(),
            total_sets = sets.toLong(),
            total_reps = reps.toLong(),
            rest_time = restTime
        )
    }

    override suspend fun updateTrainingHistoryStatus(trainingId: Long, status: String) {
        query.updateStatus(
            status = status,
            id = trainingId
        )
    }

    override fun getGroupTrainings(group: String): Flow<List<Training>> {
        return query.getGroupTrainings(group).asFlow().mapToList(Dispatchers.IO).map { trainings ->
            trainings.map { training ->
                training.toTraining()
            }
        }
    }

    override fun getParticularHistoryTrainings(trainingTemplateId: Long): Flow<List<Training>> {
        return query.getParticularTrainings(trainingTemplateId).asFlow().mapToList(Dispatchers.IO)
            .map { trainings ->
                trainings.map { training ->
                    training.toTraining()
                }
            }
    }

    override fun getTrainingsWithExercise(exerciseName: String): Flow<List<Training>> {
        return query.getTrainingsWithExercise(exerciseName).asFlow().mapToList(Dispatchers.IO)
            .map { trainings ->
                trainings.map { training ->
                    training.toTraining()
                }
            }
    }

    override suspend fun deleteTrainingHistoryRecord(trainingId: Long) {
        query.deleteTrainingRecord(id = trainingId)
        exerciseQuery.deleteTrainingExercisesRecords(training_history_id = trainingId)
    }

    override fun getLastSameTraining(trainingTemplateId: Long): Flow<Training?> {
        return query.getLastSameTraining(training_template_id = trainingTemplateId).asFlow()
            .map { trainingHistory ->
                trainingHistory.executeAsOneOrNull()?.toTraining()
            }.flowOn(Dispatchers.IO)
    }

    override fun getLastTraining(): Flow<Training?> {
        return query.getLastTraining().asFlow()
            .map { trainingHistory ->
                trainingHistory.executeAsOneOrNull()?.toTraining()
            }.flowOn(Dispatchers.IO)
    }

    override fun getSearchResults(query: String): Flow<List<Training>> {
        return this.query.getSearchResults(query).asFlow().mapToList(Dispatchers.IO)
            .map { trainings ->
                trainings.map { training ->
                    training.toTraining()
                }
            }
    }
}