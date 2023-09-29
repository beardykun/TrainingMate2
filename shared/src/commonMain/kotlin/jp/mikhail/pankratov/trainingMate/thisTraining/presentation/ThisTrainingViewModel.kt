package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThisTrainingViewModel(
    private val trainingHistoryDataSource: ITrainingHistoryDataSource,
    private val exerciseDatasource: IExerciseDatasource,
) : ViewModel() {

    private val _training = MutableStateFlow<Training?>(null)
    private val _exercises = MutableStateFlow<List<ExerciseLocal>?>(null)
    val state = combine(
        _training,
        _exercises
    ) { training, exercises ->
        ThisTrainingState(
            training = training, exerciseLocals = exercises
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        ThisTrainingState()
    )

    init {
        loadTrainingAndExercises()
    }

    private fun loadTrainingAndExercises() = viewModelScope.launch(Dispatchers.IO) {
        trainingHistoryDataSource.getOngoingTraining().collect { training ->
            training?.let { trainingNotNull ->
                _training.value = trainingNotNull
                val exercises = exerciseDatasource.getExercisesByNames(trainingNotNull.exercises).first()
                _exercises.value = exercises
            }
        }
    }

    fun onEvent(event: ThisTrainingEvent) {
        when(event) {

        }
    }
}