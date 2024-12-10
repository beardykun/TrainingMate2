package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.Utils
import jp.mikhail.pankratov.trainingMate.core.toOneDecimal
import jp.mikhail.pankratov.trainingMate.theme.darkGreen
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.average_training_score_with_args
import maxrep.shared.generated.resources.average_weight_per_exercise_with_args
import maxrep.shared.generated.resources.average_weight_per_workout_with_args
import maxrep.shared.generated.resources.average_workout_time_with_args
import maxrep.shared.generated.resources.best_training_score_with_args
import maxrep.shared.generated.resources.min_training_score_with_args
import maxrep.shared.generated.resources.number_of_done_exercises_with_args
import maxrep.shared.generated.resources.number_of_done_sets_with_args
import maxrep.shared.generated.resources.number_of_workouts_with_args
import maxrep.shared.generated.resources.this_week_summary
import maxrep.shared.generated.resources.total_lifted_weight_with_args
import maxrep.shared.generated.resources.total_reps_number_with_args
import maxrep.shared.generated.resources.total_rest_time
import maxrep.shared.generated.resources.total_training_duration_with_args
import org.jetbrains.compose.resources.stringResource

@Composable
fun SummaryWeekly(
    weeklySummary: WeeklySummary?,
    lastWeekSummary: WeeklySummary?,
    modifier: Modifier,
    onClick: (year: Long, week: Long) -> Unit
) {
    weeklySummary?.let { summary ->
        Card(
            elevation = CardDefaults.cardElevation(Dimens.cardElevation),
            modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.Padding8),
            onClick = {
                onClick.invoke(summary.year, summary.weekNumber)
            }
        ) {
            Column(modifier = modifier) {
                TextLarge(
                    text = Res.string.this_week_summary.getString()
                )
                TextMedium(
                    text = stringResource(
                        Res.string.number_of_workouts_with_args,
                        summary.numWorkouts
                    ),
                    arguments = getArgument(lastWeekSummary?.numWorkouts, summary.numWorkouts)
                )
                TextMedium(
                    text = stringResource(
                        Res.string.total_training_duration_with_args,
                        summary.trainingDuration
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.trainingDuration,
                        summary.trainingDuration
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.total_lifted_weight_with_args,
                        summary.totalLiftedWeight
                    ),
                    fontWeight = FontWeight.Bold,
                    arguments = getArgument(
                        lastWeekSummary?.totalLiftedWeight?.toInt(),
                        summary.totalLiftedWeight.toInt()
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.number_of_done_exercises_with_args,
                        summary.numExercises
                    ),
                    arguments = getArgument(lastWeekSummary?.numExercises, summary.numExercises)
                )
                TextMedium(
                    text = stringResource(
                        Res.string.number_of_done_sets_with_args,
                        summary.numSets
                    ),
                    arguments = getArgument(lastWeekSummary?.numSets, summary.numSets)
                )
                TextMedium(
                    text = stringResource(
                        Res.string.total_reps_number_with_args,
                        summary.numReps
                    ),
                    arguments = getArgument(lastWeekSummary?.numReps, summary.numReps)
                )
                TextMedium(
                    text = stringResource(
                        Res.string.total_rest_time,
                        Utils.formatTimeText(summary.totalRestTime)
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.totalRestTime?.toInt()?.div(60),
                        summary.totalRestTime.toInt() / 60
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.average_weight_per_exercise_with_args,
                        summary.avgLiftedWeightPerExercise.toOneDecimal()
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.avgLiftedWeightPerExercise?.toInt(),
                        summary.avgLiftedWeightPerExercise.toInt()
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.average_weight_per_workout_with_args,
                        summary.avgLiftedWeightPerWorkout.toOneDecimal()
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.avgLiftedWeightPerWorkout?.toInt(),
                        summary.avgLiftedWeightPerWorkout.toInt()
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.average_workout_time_with_args,
                        summary.avgDurationPerWorkout
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.avgDurationPerWorkout?.toInt(),
                        summary.avgDurationPerWorkout.toInt()
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.average_training_score_with_args,
                        summary.averageTrainingScore
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.averageTrainingScore?.toInt(),
                        summary.averageTrainingScore.toInt()
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.best_training_score_with_args,
                        summary.bestTrainingScore
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.bestTrainingScore?.toInt(),
                        summary.bestTrainingScore.toInt()
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.min_training_score_with_args,
                        summary.minTrainingScore
                    ),
                    arguments = getArgument(
                        lastWeekSummary?.minTrainingScore?.toInt(),
                        summary.minTrainingScore.toInt()
                    )
                )
            }
        }
    }
}

private fun getArgument(
    lastWeekValue: Int?,
    thisWeekValue: Int
): Array<out Pair<String, Color>> {
    val argumentValue =
        if (lastWeekValue != null) thisWeekValue - lastWeekValue else null
    val argumentColor = if (argumentValue != null && argumentValue >= 0) darkGreen else Color.Red
    return if (argumentValue != null) arrayOf(
        Pair(
            if (argumentValue > 0) "  (+$argumentValue)" else "  ($argumentValue)",
            argumentColor
        )
    ) else emptyArray()
}