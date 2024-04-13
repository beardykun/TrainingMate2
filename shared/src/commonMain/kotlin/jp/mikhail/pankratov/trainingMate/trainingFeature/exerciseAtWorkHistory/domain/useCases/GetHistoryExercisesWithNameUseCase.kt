package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.domain.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.flow.Flow

class GetExercisesWithNameUseCase(private val exerciseHistoryDatasource: IExerciseHistoryDatasource) {
    operator fun invoke(name: String): Flow<List<Exercise>> {
        return exerciseHistoryDatasource.getExercisesWithName(name)
    }
}