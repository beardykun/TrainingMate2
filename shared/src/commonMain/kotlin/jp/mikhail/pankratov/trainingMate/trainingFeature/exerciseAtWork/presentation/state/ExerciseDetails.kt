package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state

import androidx.compose.ui.text.input.TextFieldValue
import dev.icerock.moko.resources.StringResource
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty

data class ExerciseDetails(
    val exercise: Exercise? = null,
    val lastSameExercise: Exercise? = null,
    val exerciseLocal: ExerciseLocal? = null,
    val weight: TextFieldValue = TextFieldValue(""),
    val reps: TextFieldValue = TextFieldValue(""),
    val errorReps: StringResource? = null,
    val errorWeight: StringResource? = null,
    val setDifficulty: SetDifficulty = SetDifficulty.Light
)

