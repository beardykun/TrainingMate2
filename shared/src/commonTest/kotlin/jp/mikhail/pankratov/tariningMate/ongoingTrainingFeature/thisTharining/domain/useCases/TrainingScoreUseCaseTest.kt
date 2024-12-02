package jp.mikhail.pankratov.tariningMate.ongoingTrainingFeature.thisTharining.domain.useCases

import assertk.assertThat
import assertk.assertions.isEqualTo
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

        val result = useCase(training, previousTraining = null)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.first_training)
    }

    @Test
    fun `should handle weight progress with perfect score`() {
        val training = baseTraining.copy(totalLiftedWeight = 120.0)
        val previousTraining = baseTraining

        val result = useCase(training, previousTraining)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.all_progress_perfect_score)
    }

    @Test
    fun `should return reduced score for increased rest time`() {
        val training = baseTraining.copy(restTime = 700)
        val previousTraining = baseTraining

        val result = useCase(training, previousTraining)

        assertThat(result.score).isEqualTo(95)
        assertThat(result.comment).isEqualTo(Res.string.only_rest_time_regressed)
    }

    @Test
    fun `should return reduced score for decreased volume`() {
        val training = baseTraining.copy(totalSets = 8, totalReps = 80)
        val previousTraining = baseTraining

        val result = useCase(training, previousTraining)

        assertThat(result.score).isEqualTo(89)
        assertThat(result.comment).isEqualTo(Res.string.only_volume_regressed)
    }

    @Test
    fun `should return reduced score for decreased weight`() {
        val training = baseTraining.copy(totalLiftedWeight = 80.0)
        val previousTraining = baseTraining

        val result = useCase(training, previousTraining)

        assertThat(result.score).isEqualTo(92)
        assertThat(result.comment).isEqualTo(Res.string.only_weight_regressed)
    }

    @Test
    fun `should handle all regressions`() {
        val training = baseTraining.copy(
            totalLiftedWeight = 80.0,
            totalSets = 8,
            totalReps = 80,
            restTime = 700
        )
        val previousTraining = baseTraining

        val result = useCase(training, previousTraining)

        assertThat(result.score).isEqualTo(76)
        assertThat(result.comment).isEqualTo(Res.string.all_regressed)
    }

    @Test
    fun `should handle perfect performance with minor rest regression and weight progress`() {
        val training = baseTraining.copy(restTime = 650, totalLiftedWeight = 110.0)
        val previousTraining = baseTraining

        val result = useCase(training, previousTraining)

        assertThat(result.score).isEqualTo(100)
        assertThat(result.comment).isEqualTo(Res.string.only_rest_time_regressed_perfect_score)
    }
}