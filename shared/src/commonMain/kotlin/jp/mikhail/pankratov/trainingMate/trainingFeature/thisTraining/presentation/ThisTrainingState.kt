package jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.ExerciseListItem

@Immutable
data class ThisTrainingState(
    val ongoingTraining: Training? = null,
    val exerciseLocalsWithHeaders: List<ExerciseListItem>? = null,
    val exercisesLocal: List<ExerciseLocal> = emptyList(),
    val lastTraining: Training? = null,
    val timerState: TimerState = TimerState()
)

data class TimerState(val trainingTime: String = "")