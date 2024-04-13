package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.flow.Flow

class GetExerciseFromHistoryUseCase(private val exerciseHistoryDatasource: IExerciseHistoryDatasource) {

    operator fun invoke(trainingId: Long, exerciseTemplateId: Long): Flow<Exercise?> {
        return exerciseHistoryDatasource.getExerciseFromHistory(
            trainingHistoryId = trainingId,
            exerciseTemplateId = exerciseTemplateId
        )
    }
}