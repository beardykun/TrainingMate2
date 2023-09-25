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

    override fun getExercisesByGroups(groups: String): Flow<List<Exercise>> {
        val queryParams = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8")
        val groupList = groups.split(", ")
        groupList.forEachIndexed { index, s ->
            queryParams[index] = s
        }
        return queries.getExercisesByGroups(
            queryParams[0],
            queryParams[1],
            queryParams[2],
            queryParams[3],
            queryParams[4],
            queryParams[5],
            queryParams[6],
            queryParams[7]
        ).asFlow().mapToList().map { exercises ->
            exercises.map {
                it.toExercise()
            }
        }
    }

    override fun getExerciseById(exerciseId: Long): Flow<Exercise> {
        return queries.getExerciseById(exerciseId).asFlow().map {
            it.executeAsOne().toExercise()
        }
    }

    override fun getExercisesByNames(exerciseList: List<String>): Flow<List<Exercise>> {
        val adjustedExerciseList = exerciseList.toMutableList()
        while (adjustedExerciseList.size < 20) {
            adjustedExerciseList.add("") // Or any default value
        }

        return queries.getExercisesByNames(
            adjustedExerciseList[0],
            adjustedExerciseList[1],
            adjustedExerciseList[2],
            adjustedExerciseList[3],
            adjustedExerciseList[4],
            adjustedExerciseList[5],
            adjustedExerciseList[6],
            adjustedExerciseList[7],
            adjustedExerciseList[8],
            adjustedExerciseList[9],
            adjustedExerciseList[10],
            adjustedExerciseList[11],
            adjustedExerciseList[12],
            adjustedExerciseList[13],
            adjustedExerciseList[14],
            adjustedExerciseList[15],
            adjustedExerciseList[16],
            adjustedExerciseList[17],
            adjustedExerciseList[18],
            adjustedExerciseList[19]
        ).asFlow().mapToList().map { exercises ->
            exercises.map { exercise ->
                exercise.toExercise()
            }
        }
    }

    override suspend fun insertExercise(exercise: Exercise) {
        queries.insertExercise(
            id = exercise.id,
            name = exercise.name,
            image = exercise.image,
            bestLiftedWeight = exercise.bestLiftedWeight,
            exercise_group = exercise.group
        )
    }

    override suspend fun exerciseTableEmpty(): Boolean {
        return queries.countExerciseTemplates().executeAsOne() == 0L
    }
}