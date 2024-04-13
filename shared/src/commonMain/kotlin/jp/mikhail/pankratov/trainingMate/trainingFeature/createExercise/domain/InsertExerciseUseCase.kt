package jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource

class InsertExerciseUseCase(private val exerciseDatasource: IExerciseDatasource) {
    suspend operator fun invoke(
        exerciseName: String,
        exerciseGroup: String,
        usesTwoDumbbell: Boolean,
    ) {
        exerciseDatasource.insertExercise(
            ExerciseLocal(
                name = exerciseName,
                group = exerciseGroup,
                usesTwoDumbbells = usesTwoDumbbell,
                image = exerciseGroup.lowercase()
            )
        )
    }
}