package jp.mikhail.pankratov.trainingMate.mainSccreeens.training.presentation

sealed class TrainingScreenEvent {

   data class OnBottomNavigationClick(val destination: Int)
}