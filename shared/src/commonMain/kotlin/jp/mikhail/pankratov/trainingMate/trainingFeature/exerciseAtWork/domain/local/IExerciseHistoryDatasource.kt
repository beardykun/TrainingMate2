package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import kotlinx.coroutines.flow.Flow

interface IExerciseHistoryDatasource {
    fun getExerciseFromHistory(trainingHistoryId: Long, exerciseTemplateId: Long): Flow<Exercise?>
    fun getExercisesForTrainingHistory(trainingHistoryId: Long): Flow<List<Exercise>>
    fun getExercisesForTrainingWithId(trainingId: Long): Flow<List<Exercise>>
    fun getExercisesWithName(name: String): Flow<List<Exercise>>
    fun countExerciseInHistory(trainingHistoryId: Long, exerciseTemplateId: Long): Flow<Long>
    suspend fun insertExerciseHistory(exercise: Exercise)
    suspend fun updateExerciseSets(
        sets: List<String>,
        totalLiftedWeight: Double,
        trainingHistoryId: Long,
        exerciseTemplateId: Long
    )
}