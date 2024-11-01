package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError

@Immutable
data class ExerciseDetails(
    val exercise: Exercise? = null,
    val lastSameExercise: Exercise? = null,
    val exerciseLocal: ExerciseLocal? = null,
    val weight: TextFieldValue = TextFieldValue(""),
    val reps: TextFieldValue = TextFieldValue(""),
    val inputError: InputError? = null,
    val setDifficulty: SetDifficulty = SetDifficulty.Light
)

