package jp.mikhail.pankratov.trainingMate.exercise.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.ExerciseId
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.toExerciseLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseDatasource(db: TrainingDatabase) : IExerciseDatasource {

    private val queries = db.exerciseTemplateQueries
    override fun getAllExercises(): Flow<List<ExerciseLocal>> {
        return queries.getExercises().asFlow().mapToList().map { exercises ->
            exercises.map {
                it.toExerciseLocal()
            }
        }
    }

    override fun getExercisesByGroups(groups: String): Flow<List<ExerciseLocal>> {
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
                it.toExerciseLocal()
            }
        }
    }

    override fun getExerciseById(exerciseId: Long): Flow<ExerciseLocal> {
        return queries.getExerciseById(exerciseId).asFlow().map {
            it.executeAsOne().toExerciseLocal()
        }
    }

    override fun getExercisesByNames(exerciseList: List<String>): Flow<List<ExerciseLocal>> {
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
                exercise.toExerciseLocal()
            }
        }
    }

    override suspend fun insertExercise(exerciseLocal: ExerciseLocal) {
        queries.insertExercise(
            id = exerciseLocal.id,
            name = exerciseLocal.name.lowercase(),
            image = exerciseLocal.image,
            best_lifted_weight = exerciseLocal.bestLiftedWeight,
            exercise_group = exerciseLocal.group,
            uses_two_dumbbells = if (exerciseLocal.usesTwoDumbbells) 1 else 0
        )
    }

    override suspend fun exerciseTableEmpty(): Boolean {
        return queries.countExerciseTemplates().executeAsOne() == 0L
    }

    override suspend fun isExerciseExists(name: String): Boolean {
        return queries.isExerciseExists(name.lowercase()).executeAsOne() != 0L
    }

    override suspend fun updateBestLiftedWeightById(id: ExerciseId, newBestWeight: Double) {
        queries.updateBestLiftedWeightById(best_lifted_weight = newBestWeight, id = id)
    }

    override suspend fun deleteExerciseById(id: ExerciseId) {
        queries.deleteExerciseById(id)
    }
}