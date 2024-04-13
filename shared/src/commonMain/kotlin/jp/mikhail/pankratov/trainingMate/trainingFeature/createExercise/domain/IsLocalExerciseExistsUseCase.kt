package jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain

import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource

class IsLocalExerciseExistsUseCase(private val exerciseDatasource: IExerciseDatasource) {
    suspend operator fun invoke(exerciseName: String): Boolean {
        return exerciseDatasource.isExerciseExists(exerciseName) != 0L
    }
}