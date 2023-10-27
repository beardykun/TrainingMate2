package jp.mikhail.pankratov.trainingMate.exercise.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import kotlinx.coroutines.flow.Flow

interface IExerciseDatasource {
    fun getAllExercises(): Flow<List<ExerciseLocal>>
    fun getExercisesByGroups(groups: String): Flow<List<ExerciseLocal>>
    fun getExercisesByNames(exerciseList: List<String>): Flow<List<ExerciseLocal>>
    fun getExerciseById(exerciseId: Long): Flow<ExerciseLocal>
    suspend fun insertExercise(exerciseLocal: ExerciseLocal)
    suspend fun exerciseTableEmpty(): Boolean
    suspend fun isExerciseExists(name: String) : Boolean
}