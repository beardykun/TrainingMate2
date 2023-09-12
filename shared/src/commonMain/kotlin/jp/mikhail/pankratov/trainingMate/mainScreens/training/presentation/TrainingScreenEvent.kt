package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

sealed class TrainingScreenEvent {

   data class OnBottomNavigationClick(val destination: Int)
}