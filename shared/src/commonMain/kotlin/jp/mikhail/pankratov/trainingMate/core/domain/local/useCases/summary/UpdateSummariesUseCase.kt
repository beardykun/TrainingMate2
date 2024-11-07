package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.Utils
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource

class UpdateSummariesUseCase(private val summaryDatasource: ISummaryDatasource) {
    suspend operator fun invoke(ongoingTraining: Training) {
        val duration = Utils.trainingLengthToMin(ongoingTraining)
        summaryDatasource.updateSummaries(
            additionalDuration = duration,
            additionalWeight = ongoingTraining.totalLiftedWeight,
            numExercises = ongoingTraining.doneExercises.size,
            numSets = ongoingTraining.totalSets,
            numReps = ongoingTraining.totalReps,
            totalRestTime = ongoingTraining.restTime
        )
    }
}