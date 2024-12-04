package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state.ExerciseDetails
import kotlinx.datetime.Clock

class UpdateTrainingHistoryDataUseCase(private val trainingHistoryDataSource: ITrainingHistoryDataSource) {
    suspend operator fun invoke(
        exerciseDetails: ExerciseDetails,
        ongoingTraining: Training,
        trainingId: Long,
        weight: Double,
        reps: Int,
        sets: List<ExerciseSet>
    ) {
        val totalLiftedWeight = ongoingTraining.totalLiftedWeight + weight
        val doneExercises =
            ongoingTraining.doneExercises.toMutableList()
        val exerciseName = exerciseDetails.exercise?.name ?: ""

        if (!doneExercises.contains(exerciseName) && sets.isNotEmpty()) {
            doneExercises.add(exerciseName)
        } else if (doneExercises.contains(exerciseName) && sets.isEmpty()) {
            doneExercises.remove(exerciseName)
        }
        val restTime =
            if (ongoingTraining.lastDoneExercise == exerciseName) sets.lastOrNull()?.restSec
                ?: 0 else 0
        trainingHistoryDataSource.updateTrainingData(
            startTime = ongoingTraining.startTime ?: Clock.System.now()
                .toEpochMilliseconds(),
            trainingId = trainingId,
            totalLiftedWeight = totalLiftedWeight,
            doneExercised = doneExercises,
            sets = if (weight < 0) -1 else 1,
            reps = reps,
            restTime = restTime,
            lastDoneExercise = exerciseName
        )
    }
}