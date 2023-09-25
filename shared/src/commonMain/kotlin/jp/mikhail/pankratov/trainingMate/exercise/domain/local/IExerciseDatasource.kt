package jp.mikhail.pankratov.trainingMate.exercise.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import kotlinx.coroutines.flow.Flow

interface IExerciseDatasource {
    fun getAllExercises(): Flow<List<Exercise>>
    fun getExercisesByGroups(groups: String): Flow<List<Exercise>>
    fun getExercisesByNames(exerciseList: List<String>): Flow<List<Exercise>>
    fun getExerciseById(exerciseId: Long): Flow<Exercise>
    suspend fun insertExercise(exercise: Exercise)
    suspend fun exerciseTableEmpty(): Boolean

}