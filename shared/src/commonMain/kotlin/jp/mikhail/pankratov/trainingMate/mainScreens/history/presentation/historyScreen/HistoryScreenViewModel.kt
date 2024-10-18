package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.domain.TrainingQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val pageSize: Long = 10

class HistoryScreenViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val query: TrainingQuery
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            loadTrainingHistory(query)
        }
    }

    private suspend fun loadTrainingHistory(query: TrainingQuery) {
        when (query) {
            TrainingQuery.All ->
                loadPaginatedTrainings()

            is TrainingQuery.Month -> {
                val trainings = trainingUseCaseProvider.getParticularMonthHistoryTrainings()
                    .invoke(year = query.year, monthNum = query.month).first()
                _state.update {
                    it.copy(historyList = trainings)
                }
            }

            is TrainingQuery.Week -> {
                val trainings = trainingUseCaseProvider.getParticularWeekHistoryTrainings()
                    .invoke(year = query.year, weekNum = query.week).first()
                _state.update {
                    it.copy(historyList = trainings)
                }
            }
        }
    }

    fun onEvent(event: HistoryScreenEvent) {
        when (event) {
            is HistoryScreenEvent.OnDeleteClick -> {
                _state.update {
                    it.copy(
                        trainingId = event.trainingId,
                        showDeleteDialog = true
                    )
                }
            }

            HistoryScreenEvent.OnDeleteConfirmClick -> {
                _state.update {
                    it.copy(
                        trainingId = null,
                        showDeleteDialog = false
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    state.value.trainingId?.let {
                        trainingUseCaseProvider.getDeleteTrainingHistoryRecordUseCase().invoke(
                            trainingId = it
                        )
                    }
                }
            }

            HistoryScreenEvent.OnDeleteDenyClick -> {
                _state.update {
                    it.copy(
                        trainingId = null,
                        showDeleteDialog = false
                    )
                }
            }

            HistoryScreenEvent.OnLoadNextPage -> loadNextPage()
            HistoryScreenEvent.OnSearchIconClick -> {
                _state.update {
                    it.copy(
                        isExpanded = state.value.isExpanded.not()
                    )
                }
            }

            is HistoryScreenEvent.OnSearchTextChange -> {
                _state.update {
                    it.copy(
                        searchText = event.newText
                    )
                }
                viewModelScope.launch {
                    searchHistoryTrainings(event)
                }
            }
        }
    }

    private suspend fun searchHistoryTrainings(event: HistoryScreenEvent.OnSearchTextChange) {
        trainingUseCaseProvider.getHistorySearchResultsUseCase().invoke(query = event.newText)
            .collect {
                _state.update { state ->
                    state.copy(
                        historyList = it
                    )
                }
            }
    }

    private suspend fun loadPaginatedTrainings() {
        val newTrainings = trainingUseCaseProvider.getLatestHistoryTrainingsUseCase()
            .invoke(pageSize, state.value.currentPage * pageSize).first()
        _state.update {
            it.copy(
                currentPage = it.currentPage + 1,
                isLastPage = newTrainings.isEmpty(),
                historyList = (state.value.historyList ?: emptyList()).plus(newTrainings)
            )
        }
    }

    private fun loadNextPage() {
        if (query != TrainingQuery.All || state.value.searchText.isNotBlank()) return
        viewModelScope.launch {
            loadPaginatedTrainings()
        }
    }
}