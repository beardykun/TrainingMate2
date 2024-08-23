package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation


sealed class TrainingScreenEvent {
    data object OnDeleteConfirmClick : TrainingScreenEvent()
    data object OnDeleteDenyClick : TrainingScreenEvent()
    data class OnLastTrainingDelete(val trainingId: Long) : TrainingScreenEvent()
}