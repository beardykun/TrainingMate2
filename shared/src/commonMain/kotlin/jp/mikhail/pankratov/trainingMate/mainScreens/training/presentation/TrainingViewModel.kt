package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class TrainingViewModel(
    private val trainingDataSource: ITrainingDataSource,
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    private val summaryDataSource: ISummaryDatasource
) : ViewModel() {

    private val motivationalPhrases = listOf(
        "Every rep counts, make it matter!",
        "Your only limit is you. Push harder.",
        "Pain today, pride tomorrow.",
        "Sweat is just fat crying. Keep going!",
        "Train hard, or remain the same.",
        "Muscles are earned, not given.",
        "Conquer from within.",
        "Strength doesn't come from what you can do; it comes from overcoming the things you once thought you couldn't.",
        "Dream big. Lift bigger.",
        "Your body can stand almost anything. It’s your mind you have to convince.",
        "Every drop of sweat is a step closer to your goal.",
        "The best project you'll ever work on is you.",
        "Sore today, strong tomorrow.",
        "Commit to be fit.",
        "Progress, not perfection.",
        "It's not about having time, it's about making time.",
        "If it doesn’t challenge you, it doesn’t change you.",
        "You're only one workout away from a good mood.",
        "No pain, no gain. Shut up and train!",
        "Hard work beats talent when talent doesn't work hard.",
        "Wake up with determination. Go to bed with satisfaction.",
        "When you feel like quitting, remember why you started.",
        "The only bad workout is the one you didn't do.",
        "If you're tired of starting over, stop giving up.",
        "Train insane or remain the same.",
        "Push yourself because no one else is going to do it for you.",
        "The hardest step is the first one. After that, it's just momentum.",
        "Results happen over time, not overnight. Keep going.",
        "The pain you feel today will be the strength you feel tomorrow.",
        "It always seems impossible until it's done."
    )

    private val summaries =
        combine(
            summaryDataSource.getMonthlySummary(),
            summaryDataSource.getWeeklySummary()
        ) { monthly, weekly ->
            Pair(monthly, weekly)
        }

    private val trainingData = combine(
        trainingDataSource.getTrainings(),
        trainingHistoryDataSource.getOngoingTraining(),
        trainingHistoryDataSource.getLatestHistoryTrainings()
    ) { trainings, ongoingTraining, trainingsHistory ->
        Triple(trainings, ongoingTraining, trainingsHistory)
    }

    private val _state = MutableStateFlow(TrainingScreenState())
    val state = combine(
        _state,
        trainingData,
        summaries
    ) { state, trainingData, summaries ->
        val (trainings, ongoingTraining, trainingsHistory) = trainingData
        val (monthly, weekly) = summaries
        state.copy(
            availableTrainings = trainings,
            greeting = motivationalPhrases.random(),
            ongoingTraining = ongoingTraining,
            lastTrainings = trainingsHistory,
            monthlySummary = monthly,
            weeklySummary = weekly
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = TrainingScreenState()
    )

    fun onEvent(event: TrainingScreenEvent) {
        when (event) {
            is TrainingScreenEvent.OnStartNewTraining -> {
                state.value.selectedTraining?.let {
                    startNewTraining(it)
                }
            }

            is TrainingScreenEvent.OnTrainingItemClick -> {
                _state.update {
                    it.copy(
                        showStartTrainingDialog = event.shouldShowDialog,
                        selectedTraining = event.training
                    )
                }
            }

            is TrainingScreenEvent.OnLastTrainingDelete -> {
                _state.update {
                    it.copy(
                        trainingId = event.trainingId,
                        showDeleteDialog = true
                    )
                }
            }

            TrainingScreenEvent.OnDeleteConfirmClick -> {
                state.value.trainingId?.let { deleteLastTraining(trainingId = it) }
                _state.update {
                    it.copy(
                        trainingId = null,
                        showDeleteDialog = false
                    )
                }
            }

            TrainingScreenEvent.OnDeleteDenyClick -> {
                _state.update {
                    it.copy(
                        trainingId = null,
                        showDeleteDialog = false
                    )
                }
            }

            is TrainingScreenEvent.OnTrainingTemplateDelete -> {
                _state.update {
                    it.copy(
                        trainingId = event.id,
                        showDeleteTemplateDialog = true
                    )
                }
            }

            TrainingScreenEvent.OnDeleteTemplateConfirmClick -> {
                state.value.trainingId?.let { deleteTemplateTraining(trainingId = it) }
                _state.update {
                    it.copy(
                        showDeleteTemplateDialog = false,
                        trainingId = null
                    )
                }
            }

            TrainingScreenEvent.OnDeleteTemplateDenyClick -> {
                _state.update {
                    it.copy(
                        showDeleteTemplateDialog = false,
                        trainingId = null
                    )
                }
            }
        }
    }

    private fun deleteLastTraining(trainingId: Long) = viewModelScope.launch(Dispatchers.IO) {
        trainingHistoryDataSource.deleteTrainingRecord(trainingId = trainingId)
    }

    private fun deleteTemplateTraining(trainingId: Long) = viewModelScope.launch(Dispatchers.IO) {
        trainingDataSource.deleteTrainingTemplate(id = trainingId)
    }

    private fun startNewTraining(training: TrainingLocal) = viewModelScope.launch(Dispatchers.IO) {
        finishLastTrainingWhenStartingNew()
        trainingHistoryDataSource.insertTrainingRecord(
            Training(
                trainingTemplateId = training.id!!,
                name = training.name,
                groups = training.groups,
                description = training.description,
                startTime = Clock.System.now().toEpochMilliseconds(),
                userId = "1",
                exercises = training.exercises
            )
        )
        summaryDataSource.insetSummary()
    }

    private suspend fun finishLastTrainingWhenStartingNew() {
        state.value.ongoingTraining?.id?.let { ongoingTrainingId ->
            if (state.value.ongoingTraining?.totalWeightLifted == 0.0) {
                trainingHistoryDataSource.deleteTrainingRecord(ongoingTrainingId)
                return@let
            }
            trainingHistoryDataSource.updateStatus(trainingId = ongoingTrainingId)
        }
    }
}