package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases

import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state.ExerciseDetails

class UpdateBestLiftedWeightUseCase(
    private val exerciseDataSource: IExerciseDatasource
) {
    suspend operator fun invoke(exerciseDetails: ExerciseDetails) {
        val id = exerciseDetails.exerciseLocal?.id ?: -1
        val currentLiftedWeight = exerciseDetails.weight.text.toDouble()
        val bestLiftedWeight = (exerciseDetails.exerciseLocal?.bestLiftedWeight ?: 0.0)
        if (currentLiftedWeight > bestLiftedWeight) {
            exerciseDataSource.updateBestLiftedWeightById(
                id = id,
                newBestWeight = currentLiftedWeight
            )
        }
    }
}