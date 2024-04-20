package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.domain

sealed class TrainingQuery {
    data class Month(val year: Long, val month: Long) : TrainingQuery()
    data class Week(val year: Long, val week: Long) : TrainingQuery()
    data object All : TrainingQuery()
}