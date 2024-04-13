package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource

class DeleteLocalExerciseByIdUseCase(private val exerciseDatasource: IExerciseDatasource) {
    suspend operator fun invoke(id: Long) {
        exerciseDatasource.deleteExerciseById(id = id)
    }
}