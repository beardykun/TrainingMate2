package jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.domain.TrainingQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    query: TrainingQuery
) : ViewModel() {

    private val _trainingListState: MutableStateFlow<List<Training>> = MutableStateFlow(listOf())

    private val _state = MutableStateFlow(HistoryScreenState())
    val state = combine(
        _trainingListState,
        _state
    ) { historyList, state ->
        if (state.historyList != historyList) {
            state.copy(historyList = historyList)
        } else state
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = HistoryScreenState()
    )

    init {
        viewModelScope.launch {
            loadTrainingHistory(query)
        }
    }

    private suspend fun loadTrainingHistory(query: TrainingQuery) {
        val trainings = when (query) {
            TrainingQuery.All ->
                trainingUseCaseProvider.getLatestHistoryTrainingsUseCase().invoke().first()

            is TrainingQuery.Month ->
                trainingUseCaseProvider.getParticularMonthHistoryTrainings()
                    .invoke(year = query.year, monthNum = query.month).first()

            is TrainingQuery.Week ->
                trainingUseCaseProvider.getParticularWeekHistoryTrainings()
                    .invoke(year = query.year, weekNum = query.week).first()
        }
        _trainingListState.value = trainings
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
        }
    }
}