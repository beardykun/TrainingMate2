package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

data class HistoryScreenState(
    val historyList: List<Training>? = null,
    val showDeleteDialog: Boolean = false,
    val trainingId: Long? = null,
    val isLastPage: Boolean = false,
    val currentPage: Int = 0
)
