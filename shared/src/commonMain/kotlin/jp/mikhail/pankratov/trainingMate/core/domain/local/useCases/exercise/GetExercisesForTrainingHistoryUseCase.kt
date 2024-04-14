package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.flow.Flow

class GetExercisesForTrainingHistoryUseCase(private val exerciseHistoryDatasource: IExerciseHistoryDatasource) {
    operator fun invoke(trainingHistoryId: Long):Flow<List<Exercise>> {
        return exerciseHistoryDatasource.getExercisesForTrainingHistory(trainingHistoryId = trainingHistoryId)
    }
}