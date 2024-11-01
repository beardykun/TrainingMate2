package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import androidx.compose.runtime.Immutable
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training

@Immutable
data class HistoryScreenState(
    val historyList: List<Training>? = null,
    val showDeleteDialog: Boolean = false,
    val trainingId: Long? = null,
    val isLastPage: Boolean = false,
    val currentPage: Int = 0,
    val isExpanded: Boolean = false,
    val searchText: String = ""
)
