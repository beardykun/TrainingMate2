package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local

import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource

class UpdateTrainingLocalExerciseUseCase(private val trainingDataSource: ITrainingDataSource) {
    suspend operator fun invoke(exercises: List<String>, id: Long) {
        trainingDataSource.updateExercises(
            exercises = exercises,
            id = id
        )
    }
}