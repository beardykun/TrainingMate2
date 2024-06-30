package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.domain.TrainingQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

private const val pageSize: Long = 10

class HistoryScreenViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val query: TrainingQuery
) : moe.tlaster.precompose.viewmodel.ViewModel() {

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
        if (query != TrainingQuery.All) return
        viewModelScope.launch {
            loadPaginatedTrainings()
        }
    }
}