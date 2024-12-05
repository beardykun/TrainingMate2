package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.presentation

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation.ExerciseListItem
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.TrainingScore

@Immutable
data class ThisTrainingState(
    val ongoingTraining: Training? = null,
    val exerciseLocalsWithHeaders: List<ExerciseListItem>? = null,
    val exercisesLocal: List<ExerciseLocal> = emptyList(),
    val lastTraining: Training? = null,
    val recentTrainings: List<Training>? = null,
    val timerState: TimerState = TimerState(),
    val score: TrainingScore? = null
)

data class TimerState(val trainingTime: String = "")