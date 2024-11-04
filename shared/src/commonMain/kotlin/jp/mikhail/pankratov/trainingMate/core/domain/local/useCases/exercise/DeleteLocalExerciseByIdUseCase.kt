package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseDatasource

class DeleteLocalExerciseByIdUseCase(private val exerciseDatasource: IExerciseDatasource) {
    suspend operator fun invoke(id: Long) {
        exerciseDatasource.deleteExerciseById(id = id)
    }
}