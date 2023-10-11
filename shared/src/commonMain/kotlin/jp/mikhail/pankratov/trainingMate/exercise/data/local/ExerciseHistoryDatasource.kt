package jp.mikhail.pankratov.trainingMate.exercise.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.toExercise
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
            exercise_group = exercise.group,
            training_history_id = exercise.trainingHistoryId,
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
}