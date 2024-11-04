package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource

class InsertExerciseHistoryUseCase(private val exerciseHistoryDatasource: IExerciseHistoryDatasource) {
    suspend operator fun invoke(exercise: Exercise) {
        exerciseHistoryDatasource.insertExerciseHistory(exercise)
    }
}