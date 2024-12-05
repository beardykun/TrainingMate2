package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.TrainingScore
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.all_progress_perfect_score
import maxrep.shared.generated.resources.all_regressed
import maxrep.shared.generated.resources.all_regressed_perfect_score
import maxrep.shared.generated.resources.first_training
import maxrep.shared.generated.resources.only_rest_time_regressed
import maxrep.shared.generated.resources.only_rest_time_regressed_perfect_score
import maxrep.shared.generated.resources.only_volume_regressed
import maxrep.shared.generated.resources.only_volume_regressed_perfect_score
import maxrep.shared.generated.resources.only_weight_regressed
import maxrep.shared.generated.resources.only_weight_regressed_perfect_score
import maxrep.shared.generated.resources.volume_and_rest_regressed
import maxrep.shared.generated.resources.volume_and_rest_regressed_perfect_score
import maxrep.shared.generated.resources.weight_and_rest_regressed
import maxrep.shared.generated.resources.weight_and_rest_regressed_perfect_score
import maxrep.shared.generated.resources.weight_and_volume_regressed
import maxrep.shared.generated.resources.weight_and_volume_regressed_perfect_score
import maxrep.shared.generated.resources.weight_stagnation_penalty_applied

private const val MAX_SCORE_WEIGHT = 40.0
private const val MAX_SCORE = 30.0
private const val MIN_SCORE = 10.0
private const val MAX_TOTAL_SCORE = 100
private const val ZERO = 0
private const val POSITIVE_MARGIN = 5

class TrainingScoreUseCase {

    operator fun invoke(
        training: Training,
        recentTrainings: List<Training>?
    ): TrainingScore {
        // Calculate averages from recent trainings
        val avgLiftedWeight = recentTrainings?.map { it.totalLiftedWeight }?.average() ?: 0.0
        val avgTotalSets = recentTrainings?.map { it.totalSets }?.average()?.toInt() ?: 0
        val avgTotalReps = recentTrainings?.map { it.totalReps }?.average()?.toInt() ?: 0
        val avgRestTime = recentTrainings?.map { it.restTime }?.average()?.toLong() ?: 0

        // Calculate scores based on trends and averages
        val trainingWeightScore = getTrainingWeightScore(avgLiftedWeight, training)
        val trainingVolumeScore = getTrainingVolumeScore(avgTotalSets, avgTotalReps, training)
        val trainingRestTimeScore = getTrainingRestTimeScore(avgRestTime, training)
        var totalScore = (trainingWeightScore + trainingVolumeScore + trainingRestTimeScore).toInt()
            .coerceIn(ZERO, MAX_TOTAL_SCORE)

        // Calculate weight stagnation penalty only if totalScore == MAX_TOTAL_SCORE
        val weightStagnationPenalty = if (totalScore == MAX_TOTAL_SCORE) {
            recentTrainings?.let { calculateWeightStagnationPenalty(it) } ?: 0
        } else 0

        // Subtract the penalty if applicable
        totalScore -= weightStagnationPenalty
        val weightStagnationPenaltyApplied = weightStagnationPenalty > 0

        // Analyze trends
        val allProgress = trainingWeightScore >= MAX_SCORE_WEIGHT &&
                trainingVolumeScore >= MAX_SCORE &&
                trainingRestTimeScore >= MAX_SCORE
        val onlyRestTimeRegressed = trainingWeightScore >= MAX_SCORE_WEIGHT &&
                trainingVolumeScore >= MAX_SCORE &&
                trainingRestTimeScore < MAX_SCORE
        val onlyVolumeRegressed = trainingWeightScore >= MAX_SCORE_WEIGHT &&
                trainingVolumeScore < MAX_SCORE &&
                trainingRestTimeScore >= MAX_SCORE
        val onlyWeightRegressed = trainingWeightScore < MAX_SCORE_WEIGHT &&
                trainingVolumeScore >= MAX_SCORE &&
                trainingRestTimeScore >= MAX_SCORE
        val weightAndVolumeRegressed = trainingWeightScore < MAX_SCORE_WEIGHT &&
                trainingVolumeScore < MAX_SCORE &&
                trainingRestTimeScore >= MAX_SCORE
        val weightAndRestRegressed = trainingWeightScore < MAX_SCORE_WEIGHT &&
                trainingVolumeScore >= MAX_SCORE &&
                trainingRestTimeScore < MAX_SCORE
        val volumeAndRestRegressed = trainingWeightScore >= MAX_SCORE_WEIGHT &&
                trainingVolumeScore < MAX_SCORE &&
                trainingRestTimeScore < MAX_SCORE
        val allRegressed = trainingWeightScore < MAX_SCORE_WEIGHT &&
                trainingVolumeScore < MAX_SCORE &&
                trainingRestTimeScore < MAX_SCORE

        val lastTraining = recentTrainings?.firstOrNull()
        // Generate comment based on the analysis
        val comment = getTrainingComment(
            lastLiftedWeight = lastTraining?.totalLiftedWeight ?: 0.0,
            lastTotalReps = lastTraining?.totalReps ?: 0,
            allProgress = allProgress,
            totalScore = totalScore,
            onlyRestTimeRegressed = onlyRestTimeRegressed,
            onlyWeightRegressed = onlyWeightRegressed,
            onlyVolumeRegressed = onlyVolumeRegressed,
            weightAndVolumeRegressed = weightAndVolumeRegressed,
            weightAndRestRegressed = weightAndRestRegressed,
            volumeAndRestRegressed = volumeAndRestRegressed,
            allRegressed = allRegressed,
            weightStagnationPenaltyApplied = weightStagnationPenaltyApplied
        )

        return TrainingScore(
            score = totalScore,
            comment = comment
        )
    }

    private fun getTrainingComment(
        lastLiftedWeight: Double,
        lastTotalReps: Int,
        allProgress: Boolean,
        totalScore: Int,
        onlyRestTimeRegressed: Boolean,
        onlyWeightRegressed: Boolean,
        onlyVolumeRegressed: Boolean,
        weightAndVolumeRegressed: Boolean,
        weightAndRestRegressed: Boolean,
        volumeAndRestRegressed: Boolean,
        allRegressed: Boolean,
        weightStagnationPenaltyApplied: Boolean
    ) = when {
        lastLiftedWeight == 0.0 || lastTotalReps == 0 -> {
            Res.string.first_training
        }

        weightStagnationPenaltyApplied -> {
            Res.string.weight_stagnation_penalty_applied
        }

        allProgress -> {
            Res.string.all_progress_perfect_score
        }

        onlyRestTimeRegressed -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.only_rest_time_regressed_perfect_score
            } else {
                Res.string.only_rest_time_regressed
            }
        }

        onlyWeightRegressed -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.only_weight_regressed_perfect_score
            } else {
                Res.string.only_weight_regressed
            }
        }

        onlyVolumeRegressed -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.only_volume_regressed_perfect_score
            } else {
                Res.string.only_volume_regressed
            }
        }

        weightAndVolumeRegressed -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.weight_and_volume_regressed_perfect_score
            } else {
                Res.string.weight_and_volume_regressed
            }
        }

        weightAndRestRegressed -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.weight_and_rest_regressed_perfect_score
            } else {
                Res.string.weight_and_rest_regressed
            }
        }

        volumeAndRestRegressed -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.volume_and_rest_regressed_perfect_score
            } else {
                Res.string.volume_and_rest_regressed
            }
        }

        allRegressed -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.all_regressed_perfect_score
            } else {
                Res.string.all_regressed
            }
        }

        else -> {
            Res.string.first_training

        }
    }

    private fun getTrainingRestTimeScore(
        previousRestTime: Long,
        training: Training
    ) = if (previousRestTime > ZERO) {
        ((previousRestTime / training.restTime.toDouble()) * MAX_SCORE)
            .coerceIn(MIN_SCORE, (MAX_SCORE + POSITIVE_MARGIN))
    } else {
        MAX_SCORE
    }

    private fun getTrainingVolumeScore(
        previousTotalSets: Int,
        previousTotalReps: Int,
        training: Training
    ) = if (previousTotalSets > ZERO && previousTotalReps > ZERO) {
        val previousVolume = previousTotalSets * previousTotalReps
        val currentVolume = training.totalSets * training.totalReps
        ((currentVolume / previousVolume.toDouble()) * MAX_SCORE)
            .coerceIn(MIN_SCORE, (MAX_SCORE + POSITIVE_MARGIN))
    } else {
        MAX_SCORE
    }

    private fun getTrainingWeightScore(
        previousTrainingLifted: Double,
        training: Training
    ) = if (previousTrainingLifted > ZERO) {
        (training.totalLiftedWeight / previousTrainingLifted * MAX_SCORE_WEIGHT)
            .coerceIn(MIN_SCORE, (MAX_SCORE_WEIGHT + POSITIVE_MARGIN))
    } else {
        MAX_SCORE_WEIGHT
    }

    private fun calculateWeightStagnationPenalty(trainings: List<Training>): Int {
        val stagnationCount = trainings
            .map { it.totalLiftedWeight }
            .windowed(size = 3, step = 1)
            .count { window -> window.distinct().size == 1 }

        return if (stagnationCount > 0) 5 * stagnationCount else 0
    }
}