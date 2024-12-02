package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.TrainingScore
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.all_progress
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

private const val MAX_SCORE_WEIGHT = 40.0
private const val MAX_SCORE = 30.0
private const val MIN_SCORE = 10.0
private const val MAX_TOTAL_SCORE = 100
private const val DEFAULT_ZERO = 0
private const val ZERO = 0
private const val POSITIVE_MARGIN = 5

class TrainingScoreUseCase {

    operator fun invoke(
        training: Training,
        previousTraining: Training?
    ): TrainingScore {
        val previousTrainingLifted = previousTraining?.totalLiftedWeight ?: DEFAULT_ZERO.toDouble()
        val previousTotalSets = previousTraining?.totalSets ?: DEFAULT_ZERO
        val previousTotalReps = previousTraining?.totalReps ?: DEFAULT_ZERO
        val previousRestTime = previousTraining?.restTime ?: DEFAULT_ZERO.toLong()

        val trainingWeightScore = getTrainingWeightScore(previousTrainingLifted, training)

        val trainingVolumeScore = getTrainingVolumeScore(previousTotalSets, previousTotalReps, training)

        val trainingRestTimeScore = getTrainingRestTimeScore(previousRestTime, training)

        val totalScore = (trainingWeightScore + trainingVolumeScore + trainingRestTimeScore).toInt()
            .coerceIn(ZERO, MAX_TOTAL_SCORE)

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

        val comment = getTrainingComment(
            previousTrainingLifted = previousTrainingLifted,
            previousTotalReps = previousTotalReps,
            allProgress = allProgress,
            totalScore = totalScore,
            onlyRestTimeRegressed = onlyRestTimeRegressed,
            onlyWeightRegressed = onlyWeightRegressed,
            onlyVolumeRegressed = onlyVolumeRegressed,
            weightAndVolumeRegressed = weightAndVolumeRegressed,
            weightAndRestRegressed = weightAndRestRegressed,
            volumeAndRestRegressed = volumeAndRestRegressed,
            allRegressed = allRegressed
        )

        return TrainingScore(
            score = totalScore,
            comment = comment
        )
    }

    private fun getTrainingComment(
        previousTrainingLifted: Double,
        previousTotalReps: Int,
        allProgress: Boolean,
        totalScore: Int,
        onlyRestTimeRegressed: Boolean,
        onlyWeightRegressed: Boolean,
        onlyVolumeRegressed: Boolean,
        weightAndVolumeRegressed: Boolean,
        weightAndRestRegressed: Boolean,
        volumeAndRestRegressed: Boolean,
        allRegressed: Boolean
    ) = when {
        previousTrainingLifted == 0.0 || previousTotalReps == 0 -> {
            Res.string.first_training
        }

        allProgress -> {
            if (totalScore == MAX_TOTAL_SCORE) {
                Res.string.all_progress_perfect_score
            } else {
                Res.string.all_progress
            }
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
}