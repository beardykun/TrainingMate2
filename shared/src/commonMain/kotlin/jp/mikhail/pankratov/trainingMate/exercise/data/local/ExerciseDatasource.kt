package jp.mikhail.pankratov.trainingMate.exercise.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.toExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseDatasource(private val db: TrainingDatabase) : IExerciseDatasource {

    private val queries = db.exerciseTemplateQueries
    override fun getAllExercises(): Flow<List<Exercise>> {
        return queries.getExercises().asFlow().mapToList().map { exercises ->
            exercises.map {
                it.toExercise()
            }
        }
    }

    override suspend fun insertExercise(exercise: Exercise) {
        queries.insertExercise(
            id = null,
            name = exercise.name,
            image = exercise.image,
            exercise_group = exercise.group
        )
    }

    override suspend fun exerciseTableEmpty(): Boolean {
        return queries.countExerciseTemplates().executeAsOne() == 0L
    }
}