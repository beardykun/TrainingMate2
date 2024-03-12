package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.toExercise
import kotlinx.coroutines.flow.Flow
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
        }
    }

    override fun getExercisesForTrainingHistory(trainingHistoryId: Long): Flow<List<Exercise>> {
        return query.getExercisesForTrainingHistory(training_history_id = trainingHistoryId)
            .asFlow().mapToList().map { exercises ->
                exercises.map { exercise ->
                    exercise.toExercise()
                }
            }
    }

    override fun getExercisesForTrainingWithId(trainingId: Long): Flow<List<Exercise>> {
        return query.getExercisesForTrainingWithId(training_template_id = trainingId)
            .asFlow().mapToList().map { exercises ->
                exercises.map { exercise ->
                    exercise.toExercise()
                }
            }
    }

    override fun getExercisesWithName(name: String): Flow<List<Exercise>> {
        return query.getExercisesWithName(name).asFlow().mapToList().map { exercises ->
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
        }
    }

    override suspend fun insertExerciseHistory(exercise: Exercise) {
        query.insertExerciseHistory(
            id = exercise.id,
            name = exercise.name,
            sets = exercise.sets.listToString(),
            date = exercise.date,
            exercise_group = exercise.group,
            training_history_id = exercise.trainingHistoryId,
            training_template_id = exercise.trainingTemplateId,
            exercise_template_id = exercise.exerciseTemplateId,
            total_lifted_weight = exercise.totalLiftedWeight
        )
    }

    override suspend fun updateExerciseSets(
        sets: List<String>,
        totalLiftedWeight: Double,
        trainingHistoryId: Long,
        exerciseTemplateId: Long
    ) {
        query.updateExerciseSets(
            sets = sets.listToString(),
            total_lifted_weight = totalLiftedWeight,
            training_history_id = trainingHistoryId,
            exercise_template_id = exerciseTemplateId
        )
    }

    override fun getLatsSameExercise(
        exerciseTemplateId: Long,
        trainingHistoryId: Long
    ): Flow<Exercise?> {
        return query.getLastSameExercise(
            exercise_template_id = exerciseTemplateId,
            training_history_id = trainingHistoryId
        ).asFlow().map {
            it.executeAsOneOrNull()?.toExercise()
        }
    }
}