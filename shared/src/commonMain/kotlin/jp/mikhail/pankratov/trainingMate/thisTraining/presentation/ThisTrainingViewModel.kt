package jp.mikhail.pankratov.trainingMate.thisTraining.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThisTrainingViewModel(
    private val trainingDataSource: ITrainingDataSource,
    private val exerciseDatasource: IExerciseDatasource,
    private val trainingId: Long
) : ViewModel() {

    private val _training = MutableStateFlow<Training?>(null)
    private val _exercises = MutableStateFlow<List<Exercise>?>(null)
    val state = combine(
        _training,
        _exercises
    ) { training, exercises ->
        ThisTrainingState(
            training = training, exercises = exercises
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
        trainingDataSource.getTrainingById(trainingId).collect { training ->
            _training.value = training
            val exercises = exerciseDatasource.getExercisesByNames(training.exercises).first()
            _exercises.value = exercises
        }
    }

    fun onEvent(event: ThisTrainingEvent) {

    }
}