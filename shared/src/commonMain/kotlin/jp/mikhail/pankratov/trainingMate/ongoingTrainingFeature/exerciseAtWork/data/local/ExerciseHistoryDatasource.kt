package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.setListToString
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.toExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ExerciseHistoryDatasource(db: TrainingDatabase) : IExerciseHistoryDatasource {

    private val query = db.exerciseHistoryQueries
    override fun getExerciseFromHistory(
        trainingHistoryId: Long,
        exerciseTemplateId: Long
    ): Flow<Exercise?> {
        return query.getExerciseFromHistory(
            training_history_id = trainingHistoryId,
            exercise_template_id = exerciseTemplateId
        ).asFlow().map { exerciseHistory ->
            exerciseHistory.executeAsOneOrNull()?.toExercise()
        }.flowOn(Dispatchers.IO)
    }

    override fun getExercisesForTrainingHistory(trainingHistoryId: Long): Flow<List<Exercise>> {
        return query.getExercisesForTrainingHistory(training_history_id = trainingHistoryId)
            .asFlow().mapToList(Dispatchers.IO).map { exercises ->
                exercises.map { exercise ->
                    exercise.toExercise()
                }
            }
    }

    override fun getExercisesForTrainingWithId(trainingId: Long): Flow<List<Exercise>> {
        return query.getExercisesForTrainingWithId(training_template_id = trainingId)
            .asFlow().mapToList(Dispatchers.IO).map { exercises ->
                exercises.map { exercise ->
                    exercise.toExercise()
                }
            }
    }

    override fun getHistoryExercisesWithName(name: String): Flow<List<Exercise>> {
        return query.getExercisesWithName(name).asFlow().mapToList(Dispatchers.IO)
            .map { exercises ->
                exercises.map { exercise ->
                    exercise.toExercise()
                }
            }
    }

    override fun countExerciseInHistory(
        trainingHistoryId: Long,
        exerciseTemplateId: Long
    ): Flow<Long> {
        return query.countExerciseInHistory(
            training_history_id = trainingHistoryId,
            exercise_template_id = exerciseTemplateId
        ).asFlow().map { count ->
            count.executeAsOne()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertExerciseHistory(exercise: Exercise) {
        query.insertExerciseHistory(
            id = exercise.id,
            name = exercise.name,
            sets = exercise.sets.setListToString(),
            reps = exercise.reps.toLong(),
            date = exercise.date,
            exercise_group = exercise.group,
            training_history_id = exercise.trainingHistoryId,
            training_template_id = exercise.trainingTemplateId,
            exercise_template_id = exercise.exerciseTemplateId,
            total_lifted_weight = exercise.totalLiftedWeight
        )
    }

    override suspend fun updateExerciseData(
        sets: List<ExerciseSet>,
        totalLiftedWeight: Double,
        trainingHistoryId: Long,
        exerciseTemplateId: Long,
        reps: Int
    ) {
        query.updateExerciseSets(
            sets = sets.setListToString(),
            total_lifted_weight = totalLiftedWeight,
            training_history_id = trainingHistoryId,
            exercise_template_id = exerciseTemplateId,
            reps = reps.toLong()
        )
    }

    override fun getLatsSameExercise(
        exerciseTemplateId: Long,
        trainingHistoryId: Long,
        trainingTemplateId: Long
    ): Flow<Exercise?> {
        return query.getLastSameExercise(
            exercise_template_id = exerciseTemplateId,
            training_history_id = trainingHistoryId,
            training_template_id = trainingTemplateId
        ).asFlow().map {
            it.executeAsOneOrNull()?.toExercise()
        }.flowOn(Dispatchers.IO)
    }
}