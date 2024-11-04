package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.createExercise.domain

import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseDatasource

class IsLocalExerciseExistsUseCase(private val exerciseDatasource: IExerciseDatasource) {
    suspend operator fun invoke(exerciseName: String): Boolean {
        return exerciseDatasource.isExerciseExists(exerciseName) != 0L
    }
}