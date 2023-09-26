package jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
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
    trainingDataSource: ITrainingDataSource,
    private val trainingHistoryDataSource: ITrainingHistoryDataSource
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


    private val _state = MutableStateFlow(TrainingScreenState())
    val state = combine(
        _state,
        trainingDataSource.getTrainings(),
        trainingHistoryDataSource.getOngoingTraining()
    ) { state, trainings, ongoingTraining ->
        if (state.availableTrainings != trainings) {
            state.copy(
                availableTrainings = trainings,
                greeting = motivationalPhrases.random(),
                ongoingTraining = ongoingTraining
            )
        } else state
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
        }
    }

    private fun startNewTraining(training: Training) = viewModelScope.launch(Dispatchers.IO) {
        trainingHistoryDataSource.insertTrainingRecord(
            Training(
                trainingTemplateId = training.id!!,
                name = training.name,
                groups = training.groups,
                description = training.description,
                startTime = Clock.System.now().toEpochMilliseconds(),
                userId = training.userId,
                exercises = training.exercises
            )
        )
    }
}