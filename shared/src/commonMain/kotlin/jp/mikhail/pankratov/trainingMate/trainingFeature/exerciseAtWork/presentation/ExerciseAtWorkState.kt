package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class ExerciseAtWorkState(
    val ongoingTraining: Training? = null,
    val exercise: Exercise? = null,
    val lastSameExercise: Exercise? = null,
    val exerciseLocal: ExerciseLocal? = null,
    val timerValue: Int = 60,
    val timer: Int = 60,
    val isExpanded: Boolean = false,
    val weight: TextFieldValue = TextFieldValue(""),
    val reps: TextFieldValue = TextFieldValue(""),
    val errorReps: String? = null,
    val errorWeight: String? = null,
    val isDeleteDialogVisible: Boolean = false,
    val deleteItem: ExerciseSet? = null,
    val isAnimating: Boolean = false,
    val setDifficulty: String = SetDifficulty.Light.name
)