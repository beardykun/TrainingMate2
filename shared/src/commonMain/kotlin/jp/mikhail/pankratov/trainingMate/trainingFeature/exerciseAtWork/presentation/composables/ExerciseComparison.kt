package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.util.Utils
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall

@Composable
fun ExerciseComparison(
    lastExercise: Exercise?,
    exercise: Exercise,
    onClick: (name: String) -> Unit
) {
    val weightTextColor =
        if (exercise.totalLiftedWeight >= (lastExercise?.totalLiftedWeight ?: 0.0))
            Color.Blue
        else Color.Red
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = Dimens.Padding16, start = Dimens.Padding16, end = Dimens.Padding16)
            .clip(RoundedCornerShape(percent = 30))
            .padding(vertical = Dimens.Padding8)

    ) {
        lastExercise?.let { lastExercise ->
            LastExerciseData(onClick, lastExercise, exercise, Modifier.weight(1f))
        }

        CurrentExerciseData(weightTextColor, exercise, Modifier.weight(1f))
    }
}

@Composable
fun CurrentExerciseData(
    weightTextColor: Color,
    exercise: Exercise,
    modifier: Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = Dimens.Padding8),
        elevation = CardDefaults.cardElevation(Dimens.cardElevation)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextLarge(
                color = weightTextColor,
                text =
                stringResource(
                    SharedRes.strings.this_exercise,
                    Utils.formatTimeText(exercise.sets.sumOf { it.restSec ?: 0 }),
                    exercise.totalLiftedWeight,
                    exercise.sets.size
                ),
                modifier = Modifier.padding(all = Dimens.Padding8)
            )
        }
    }
}

@Composable
fun LastExerciseData(
    onClick: (name: String) -> Unit,
    lastExercise: Exercise,
    exercise: Exercise,
    modifier: Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = Dimens.Padding8),
        elevation = CardDefaults.cardElevation(Dimens.cardElevation),
        onClick = { onClick.invoke(lastExercise.name) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(all = Dimens.Padding8)) {
                TextLarge(
                    text =
                    stringResource(
                        SharedRes.strings.last_exercise,
                        Utils.formatTimeText(lastExercise.sets.sumOf { it.restSec ?: 0 }),
                        lastExercise.totalLiftedWeight,
                        lastExercise.sets.size
                    ),
                )
                val setNum = exercise.sets.size
                if (setNum < lastExercise.sets.size) {
                    val setNumToDisplay = exercise.sets.size + 1
                    val set = lastExercise.sets[setNum]
                    val background = Utils.setDifficultyColor(set.difficulty)
                    TextLarge(
                        text = stringResource(
                            SharedRes.strings.last_exercise_next_set,
                            setNumToDisplay,
                            set.weight,
                            set.reps
                        ),
                        modifier = Modifier.padding(top = Dimens.Padding4)
                            .background(background)
                    )
                    set.restTimeText?.let {
                        TextSmall(
                            text = stringResource(
                                SharedRes.strings.interval,
                                it
                            )
                        )
                    }
                }
            }
        }
    }
}