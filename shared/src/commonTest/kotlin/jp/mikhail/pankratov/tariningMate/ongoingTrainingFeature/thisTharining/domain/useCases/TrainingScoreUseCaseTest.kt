package jp.mikhail.pankratov.tariningMate.ongoingTrainingFeature.thisTharining.domain.useCases

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import assertk.assertions.isNotEqualTo
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.useCases.TrainingScoreUseCase
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.all_progress_perfect_score
import maxrep.shared.generated.resources.all_regressed
import maxrep.shared.generated.resources.first_training
import maxrep.shared.generated.resources.only_rest_time_regressed
import maxrep.shared.generated.resources.only_rest_time_regressed_perfect_score
import maxrep.shared.generated.resources.only_volume_regressed
import maxrep.shared.generated.resources.only_weight_regressed
import maxrep.shared.generated.resources.only_weight_regressed_perfect_score
import maxrep.shared.generated.resources.weight_and_rest_regressed
import maxrep.shared.generated.resources.weight_and_rest_regressed_perfect_score
import maxrep.shared.generated.resources.weight_stagnation_penalty_applied
import kotlin.test.BeforeTest
import kotlin.test.Test


class TrainingScoreUseCaseTest {
    private lateinit var useCase: TrainingScoreUseCase

    private val baseTraining = Training(
        totalLiftedWeight = 100.0,
        totalSets = 10,
        totalReps = 100,
        restTime = 600,
        userId = "1",
        name = "",
        groups = ""
    )

    @BeforeTest
    fun setup() {
        useCase = TrainingScoreUseCase()
    }

    @Test
    fun `should return perfect score for first training`() {
        val training = baseTraining
        val result = useCase(training = training, recentTrainings = emptyList())

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.first_training)
    }

    @Test
    fun `should handle weight progress with perfect score`() {
        val training = baseTraining.copy(totalLiftedWeight = 120.0)
        val previousTrainings = listOf(baseTraining)

        val result = useCase(training = training, recentTrainings = previousTrainings)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.all_progress_perfect_score)
    }

    @Test
    fun `should return reduced score for increased rest time`() {
        val training = baseTraining.copy(restTime = 700)
        val previousTrainings = listOf(baseTraining)

        val result = useCase(training = training, recentTrainings = previousTrainings)

        assertThat(result.score).isEqualTo(95)
        assertThat(result.comment).isEqualTo(Res.string.only_rest_time_regressed)
    }

    @Test
    fun `should calculate perfect score based on weight progress`() {
        val training1 = baseTraining.copy(totalLiftedWeight = 110.0)
        val training2 = baseTraining.copy(totalLiftedWeight = 115.0)
        val previousTrainings1 = listOf(
            baseTraining.copy(totalLiftedWeight = 90.0),
            baseTraining.copy(totalLiftedWeight = 100.0)
        )
        val previousTrainings2 = listOf(
            baseTraining.copy(totalLiftedWeight = 100.0),
            baseTraining.copy(totalLiftedWeight = 105.0)
        )

        val result1 = useCase(training = training1, recentTrainings = previousTrainings1)
        val result2 = useCase(training = training2, recentTrainings = previousTrainings2)

        assertThat(result1.score).isEqualTo(100)
        assertThat(result1.comment).isEqualTo(Res.string.all_progress_perfect_score)

        assertThat(result2.score).isEqualTo(100)
        assertThat(result2.comment).isEqualTo(Res.string.all_progress_perfect_score)
    }

    @Test
    fun `should handle reduced score for decreased volume across multiple trainings`() {
        val training = baseTraining.copy(totalSets = 8, totalReps = 80)
        val previousTrainings = listOf(
            baseTraining.copy(totalSets = 12, totalReps = 120),
            baseTraining.copy(totalSets = 10, totalReps = 100)
        )

        val result = useCase(training = training, recentTrainings = previousTrainings)

        assertThat(result.score).isEqualTo(85)
        assertThat(result.comment).isEqualTo(Res.string.only_volume_regressed)
    }

    @Test
    fun `should handle minor regressions while maintaining high scores`() {
        val training = baseTraining.copy(
            totalLiftedWeight = 99.0,
            restTime = 610,
            totalSets = 10,
            totalReps = 110
        )
        val previousTrainings = listOf(
            baseTraining.copy(totalLiftedWeight = 100.0, restTime = 600)
        )

        val result = useCase(training = training, recentTrainings = previousTrainings)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.weight_and_rest_regressed_perfect_score)
    }

    @Test
    fun `should average metrics over last five trainings`() {
        val training = baseTraining.copy(totalLiftedWeight = 105.0, totalSets = 9, totalReps = 90)
        val previousTrainings = listOf(
            baseTraining.copy(totalLiftedWeight = 100.0, totalSets = 10, totalReps = 100),
            baseTraining.copy(totalLiftedWeight = 102.0, totalSets = 9, totalReps = 95),
            baseTraining.copy(totalLiftedWeight = 98.0, totalSets = 8, totalReps = 85),
            baseTraining.copy(totalLiftedWeight = 97.0, totalSets = 7, totalReps = 80),
            baseTraining.copy(totalLiftedWeight = 110.0, totalSets = 9, totalReps = 90)
        )

        val result = useCase(training = training, recentTrainings = previousTrainings)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.all_progress_perfect_score)
    }

    @Test
    fun `should reward recent progress despite older peak regressions`() {
        val training = baseTraining.copy(totalLiftedWeight = 110.0)
        val previousTrainings = listOf(
            baseTraining.copy(totalLiftedWeight = 100.0), // Most recent (better than this)
            baseTraining.copy(totalLiftedWeight = 90.0),  // Second most recent (better than this)
            baseTraining.copy(totalLiftedWeight = 120.0), // Older peak (worse than this)
            baseTraining.copy(totalLiftedWeight = 125.0), // Older peak (worse than this)
            baseTraining.copy(totalLiftedWeight = 130.0)  // Oldest peak (worse than this)
        )

        val result = useCase(training, previousTrainings)

        assertThat(result.score).isGreaterThan(90) // Indicates progress.
        assertThat(result.score).isLessThan(100)  // Not perfect due to older peak regressions.
        assertThat(result.comment).isEqualTo(Res.string.only_weight_regressed)
    }

    @Test
    fun `should apply stagnation penalty if total score is 100`() {
        val training = baseTraining.copy(totalLiftedWeight = 100.0)
        val previousTrainings = listOf(
            baseTraining.copy(totalLiftedWeight = 100.0),
            baseTraining.copy(totalLiftedWeight = 100.0),
            baseTraining.copy(totalLiftedWeight = 100.0),
            baseTraining.copy(totalLiftedWeight = 100.0),
            baseTraining.copy(totalLiftedWeight = 100.0)
        )

        val result = useCase(training, previousTrainings)

        assertThat(result.score).isEqualTo(85) // Indicates progress.
        assertThat(result.comment).isEqualTo(Res.string.weight_stagnation_penalty_applied)
    }

    @Test
    fun `should handle only weight regressed with perfect score`() {
        val training = baseTraining.copy(
            totalLiftedWeight = 90.0, // Decreased weight
            totalSets = 15,
            totalReps = 150,
            restTime = 500 // Improved rest time
        )
        val recentTrainings = listOf(baseTraining)

        val result = useCase(training, recentTrainings)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.only_weight_regressed_perfect_score)
    }

    @Test
    fun `should handle weight and rest regressed`() {
        val training = baseTraining.copy(
            totalLiftedWeight = 90.0, // Decreased weight
            totalSets = 10,
            totalReps = 100,
            restTime = 700 // Increased rest time
        )
        val recentTrainings = listOf(baseTraining)

        val result = useCase(training, recentTrainings)

        assertThat(result.score).isEqualTo(91) // Assuming weight and rest penalty applied
        assertThat(result.comment).isEqualTo(Res.string.weight_and_rest_regressed)
    }

    @Test
    fun `should handle only rest time regressed with perfect score`() {
        val training = baseTraining.copy(
            totalLiftedWeight = 120.0, // Improved weight
            totalSets = 10,
            totalReps = 100,
            restTime = 700 // Increased rest time
        )
        val recentTrainings = listOf(baseTraining)

        val result = useCase(training, recentTrainings)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.only_rest_time_regressed_perfect_score)
    }

    @Test
    fun `should handle all regressed`() {
        val training = baseTraining.copy(
            totalLiftedWeight = 80.0, // Decreased weight
            totalSets = 8, // Decreased volume
            totalReps = 80,
            restTime = 700 // Increased rest time
        )
        val recentTrainings = listOf(baseTraining)

        val result = useCase(training, recentTrainings)

        assertThat(result.score).isEqualTo(76) // Assuming combined penalties
        assertThat(result.comment).isEqualTo(Res.string.all_regressed)
    }

    @Test
    fun `should not return all progress for non-perfect scores`() {
        val training = baseTraining.copy(
            totalLiftedWeight = 90.0, // Below max
            totalSets = 12,
            totalReps = 120,
            restTime = 580
        )
        val recentTrainings = listOf(
            baseTraining.copy(totalLiftedWeight = 100.0),
            baseTraining.copy(totalLiftedWeight = 100.0),
            baseTraining.copy(totalLiftedWeight = 100.0)
        )

        val result = useCase(training, recentTrainings)

        assertThat(result.comment).isNotEqualTo(Res.string.all_progress_perfect_score)
    }
}