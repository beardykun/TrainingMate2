package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.summary.WeeklySummary
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium

@Composable
fun SummaryWeekly(
    weeklySummary: WeeklySummary?,
    modifier: Modifier,
    onClick: (year: Long, week: Long) -> Unit
) {
    weeklySummary?.let { summary ->
        Card(
            elevation = CardDefaults.cardElevation(Dimens.cardElevation),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant),
            modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.Padding8),
            onClick = {
                onClick.invoke(summary.year, summary.weekNumber)
            }
        ) {
            Column(modifier = modifier) {
                val topText =
                        SharedRes.strings.this_week_summary.getString()

                TextLarge(text = topText)
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.number_of_workouts_with_args,
                        summary.numWorkouts
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.total_training_duration_with_args,
                        summary.trainingDuration
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.total_lifted_weight_with_args,
                        summary.totalLiftedWeight
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.number_of_done_exercises_with_args,
                        summary.numExercises
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.number_of_done_sets_with_args,
                        summary.numSets
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.total_reps_number_with_args,
                        summary.numReps
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.average_weight_per_exercise_with_args,
                        summary.avgLiftedWeightPerExercise
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.average_weight_per_workout_with_args,
                        summary.avgLiftedWeightPerWorkout
                    )
                )
                TextMedium(
                    text = stringResource(
                        SharedRes.strings.average_workout_time_with_args,
                        summary.avgDurationPerWorkout
                    )
                )
            }
        }
    }
}