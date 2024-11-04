package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import kotlinx.coroutines.flow.Flow

typealias ExerciseId = Long

interface IExerciseDatasource {
    fun getAllLocalExercises(): Flow<List<ExerciseLocal>>
    fun getExercisesByGroups(groups: String): Flow<List<ExerciseLocal>>
    fun getExercisesByNames(exerciseList: List<String>): Flow<List<ExerciseLocal>>
    fun getExerciseById(exerciseId: Long): Flow<ExerciseLocal>
    fun getStrengthDefineExercises(): Flow<List<ExerciseLocal>>
    suspend fun insertExercise(exerciseLocal: ExerciseLocal)
    suspend fun exerciseTableEmpty(): Boolean
    suspend fun isExerciseExists(name: String): Long
    suspend fun updateBestLiftedWeightById(id: ExerciseId, newBestWeight: Double)
    suspend fun deleteExerciseById(id: ExerciseId)
}