package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class HistoryScreenState(
    val historyList: List<Training>? = null
)
