package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.domain.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider

class RemoveExerciseUseCase(val trainingUseCaseProvider: TrainingUseCaseProvider) {

    suspend inline fun removeExercise(
        ongoingTraining: Training,
        exercises: List<String>,
        selectedExercise: String
    ) {
        val updatedExercises = exercises.minus(selectedExercise)
        trainingUseCaseProvider.getInsertTrainingHistoryRecordUseCase().invoke(
            training = ongoingTraining.copy(
                exercises = updatedExercises
            )
        )
        trainingUseCaseProvider.getUpdateTrainingLocalExerciseUseCase().invoke(
            exercises = updatedExercises,
            id = ongoingTraining.trainingTemplateId
        )
    }
}