package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import kotlinx.coroutines.flow.Flow

class GetLatsSameHistoryExerciseUseCase(private val exerciseHistoryDatasource: IExerciseHistoryDatasource) {
    operator fun invoke(
        exerciseTemplateId: Long,
        trainingId: Long
    ): Flow<Exercise?> {
        return exerciseHistoryDatasource.getLatsSameExercise(
            exerciseTemplateId = exerciseTemplateId,
            trainingHistoryId = trainingId
        )
    }
}