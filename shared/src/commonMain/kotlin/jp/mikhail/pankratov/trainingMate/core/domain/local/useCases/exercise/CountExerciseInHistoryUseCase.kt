package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.flow.Flow

class CountExerciseInHistoryUseCase(private val exerciseHistoryDatasource: IExerciseHistoryDatasource) {
    operator fun invoke(
        trainingHistoryId: Long,
        exerciseTemplateId: Long
    ): Flow<Long> {
        return exerciseHistoryDatasource.countExerciseInHistory(
            trainingHistoryId = trainingHistoryId, exerciseTemplateId = exerciseTemplateId
        )
    }
}