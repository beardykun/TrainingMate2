package jp.mikhail.pankratov.trainingMate.exercise.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import kotlinx.coroutines.flow.Flow

interface IExerciseDatasource {
    fun getAllExercises(): Flow<List<Exercise>>
    fun getExercisesByGroups(groups: String): Flow<List<Exercise>>
    suspend fun insertExercise(exercise: Exercise)
    suspend fun exerciseTableEmpty(): Boolean

}