package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class ExerciseAtWorkState(
    val ongoingTraining: Training? = null,
    val timerState: TimerState = TimerState(),
    val exerciseDetails: ExerciseDetails = ExerciseDetails(),
    val uiState: UIState = UIState()
)