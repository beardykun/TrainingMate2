package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.MonthlySummary
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.average_weight_per_exercise_with_args
import maxrep.shared.generated.resources.average_weight_per_workout_with_args
import maxrep.shared.generated.resources.average_workout_time_with_args
import maxrep.shared.generated.resources.last_month_summary
import maxrep.shared.generated.resources.number_of_done_exercises_with_args
import maxrep.shared.generated.resources.number_of_done_sets_with_args
import maxrep.shared.generated.resources.number_of_workouts_with_args
import maxrep.shared.generated.resources.this_month_summary
import maxrep.shared.generated.resources.total_lifted_weight_with_args
import maxrep.shared.generated.resources.total_reps_number_with_args
import maxrep.shared.generated.resources.total_training_duration_with_args
import org.jetbrains.compose.resources.stringResource

@Composable
fun SummaryMonthly(
    monthlySummary: MonthlySummary?,
    modifier: Modifier,
    counter: Int,
    onClick: (year: Long, month: Long) -> Unit
) {
    monthlySummary?.let { summary ->
        Card(
            elevation = CardDefaults.cardElevation(Dimens.cardElevation),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant),
            modifier = Modifier.padding(horizontal = Dimens.Padding8),
            onClick = { onClick.invoke(summary.year, summary.monthNumber) }
        ) {
            Column(modifier = modifier) {
                val topText =
                    if (counter == 0)
                        Res.string.this_month_summary.getString()
                    else
                        Res.string.last_month_summary.getString()
                TextLarge(topText)
                TextMedium(
                    text = stringResource(
                        Res.string.number_of_workouts_with_args,
                        summary.numWorkouts
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.total_training_duration_with_args,
                        summary.trainingDuration
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.total_lifted_weight_with_args,
                        summary.totalLiftedWeight
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.number_of_done_exercises_with_args,
                        summary.numExercises
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.number_of_done_sets_with_args,
                        summary.numSets
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.total_reps_number_with_args,
                        summary.numReps
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.average_weight_per_exercise_with_args,
                        summary.avgLiftedWeightPerExercise
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.average_weight_per_workout_with_args,
                        summary.avgLiftedWeightPerWorkout
                    )
                )
                TextMedium(
                    text = stringResource(
                        Res.string.average_workout_time_with_args,
                        summary.avgDurationPerWorkout
                    )
                )
            }
        }
    }
}