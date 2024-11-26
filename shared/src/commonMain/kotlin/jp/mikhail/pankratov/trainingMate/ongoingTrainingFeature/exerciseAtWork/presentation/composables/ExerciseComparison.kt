package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables

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
import androidx.compose.ui.text.font.FontWeight
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall
import jp.mikhail.pankratov.trainingMate.core.presentation.utils.Utils
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.interval
import maxrep.shared.generated.resources.last_exercise
import maxrep.shared.generated.resources.last_exercise_next_set
import maxrep.shared.generated.resources.this_exercise
import maxrep.shared.generated.resources.this_exercise_length
import maxrep.shared.generated.resources.this_exercise_lifted
import maxrep.shared.generated.resources.this_exercise_sets
import maxrep.shared.generated.resources.this_exercise_with_args
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExerciseComparison(
    lastExercise: Exercise?,
    exercise: Exercise,
    onClick: (name: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = Dimens.Padding16, start = Dimens.Padding16, end = Dimens.Padding16)
            .clip(RoundedCornerShape(percent = 30))
            .padding(vertical = Dimens.Padding8)

    ) {
        lastExercise?.let { lastExercise ->
            LastExerciseData(onClick, lastExercise, exercise, Modifier.weight(1f))
        }

        CurrentExerciseData(
            lastExercise = lastExercise,
            exercise = exercise,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CurrentExerciseData(
    lastExercise: Exercise?,
    exercise: Exercise,
    modifier: Modifier
) {
    val weightTextColor =
        if (exercise.totalLiftedWeight >= (lastExercise?.totalLiftedWeight ?: 0.0))
            Color.Blue
        else Color.Red
    val sumOfRest = exercise.sets.sumOf { it.restSec ?: 0 }
    val restColor = if (sumOfRest > (lastExercise?.sets?.sumOf { it.restSec ?: 0 }
            ?: 0)) Color.Red else Color.Blue
    val setColor =
        if (exercise.sets.size >= (lastExercise?.sets?.size ?: 0)) Color.Blue else Color.Red
    Card(
        modifier = modifier.padding(horizontal = Dimens.Padding8),
        elevation = CardDefaults.cardElevation(Dimens.cardElevation)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextMedium(
                text = Res.string.this_exercise.getString(),
                arguments = arrayOf(
                    Pair(
                        Res.string.this_exercise_length.getString(),
                        Color.Unspecified
                    ),
                    Pair(
                        Utils.formatTimeText(sumOfRest),
                        restColor
                    ),
                    Pair(
                        Res.string.this_exercise_lifted.getString(),
                        Color.Unspecified
                    ),
                    Pair(
                        exercise.totalLiftedWeight.toString(),
                        weightTextColor
                    ),
                    Pair(
                        Res.string.this_exercise_sets.getString(),
                        Color.Unspecified
                    ),
                    Pair(
                        exercise.sets.size.toString(),
                        setColor
                    )
                ),
                fontWeight = FontWeight.Bold,
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
                TextMedium(
                    text =
                    stringResource(
                        Res.string.last_exercise,
                        Utils.formatTimeText(lastExercise.sets.sumOf { it.restSec ?: 0 }),
                        lastExercise.totalLiftedWeight,
                        lastExercise.sets.size
                    ),
                    fontWeight = FontWeight.Bold
                )
                val setNum = exercise.sets.size
                if (setNum < lastExercise.sets.size) {
                    val setNumToDisplay = exercise.sets.size + 1
                    val set = lastExercise.sets[setNum]
                    val background = Utils.setDifficultyColor(set.difficulty)
                    TextMedium(
                        text = stringResource(
                            Res.string.last_exercise_next_set,
                            setNumToDisplay,
                            set.weight,
                            set.reps
                        ),
                        modifier = Modifier
                            .padding(top = Dimens.Padding2)
                            .background(color = background, shape = RoundedCornerShape(100))
                            .padding(all = Dimens.Padding2)
                    )
                    set.restTimeText?.let {
                        TextSmall(
                            text = stringResource(
                                Res.string.interval,
                                it
                            )
                        )
                    }
                }
            }
        }
    }
}