package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseDatasource

class InsertLocalExerciseUseCase(private val exerciseDatasource: IExerciseDatasource) {
    suspend operator fun invoke(exerciseLocal: ExerciseLocal) {
        exerciseDatasource.insertExercise(exerciseLocal)
    }
}