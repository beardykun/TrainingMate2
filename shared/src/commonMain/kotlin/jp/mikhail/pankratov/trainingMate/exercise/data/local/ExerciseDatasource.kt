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

    override fun getExercisesByNames(exerciseList: List<String>): Flow<List<Exercise>> {
        val queryParams = mutableListOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20"
        )
        exerciseList.forEachIndexed { index, s ->
            queryParams[index] = s
        }

        return queries.getExercisesByNames(
            queryParams[0],
            queryParams[1],
            queryParams[2],
            queryParams[3],
            queryParams[4],
            queryParams[5],
            queryParams[6],
            queryParams[7],
            queryParams[8],
            queryParams[9],
            queryParams[10],
            queryParams[11],
            queryParams[12],
            queryParams[13],
            queryParams[14],
            queryParams[15],
            queryParams[16],
            queryParams[17],
            queryParams[18],
            queryParams[19]
        ).asFlow().mapToList().map { exercises ->
            exercises.map { exercise ->
                exercise.toExercise()
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