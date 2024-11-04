package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state.ExerciseDetails

class UpdateHistoryExerciseDataUseCase(private val exerciseHistoryDatasource: IExerciseHistoryDatasource) {
    suspend operator fun invoke(
        exerciseDetails: ExerciseDetails,
        sets: List<ExerciseSet>,
        weight: Double,
        reps: Int,
        trainingId: Long,
        exerciseTemplateId: Long
    ) {
        val exerciseTotalLifted = (exerciseDetails.exercise?.totalLiftedWeight ?: 0.0) + weight
        exerciseHistoryDatasource.updateExerciseData(
            sets = sets,
            totalLiftedWeight = exerciseTotalLifted,
            trainingHistoryId = trainingId,
            exerciseTemplateId = exerciseTemplateId,
            reps = reps
        )
    }
}