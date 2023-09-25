package jp.mikhail.pankratov.trainingMate.exercise.presentation

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

class ExerciseAtWorkViewModel(
    private val trainingDataSource: ITrainingDataSource,
    private val exerciseDatasource: IExerciseDatasource,
    //FIXME need to create training history and pass it here instead
    private val trainingId: Long,
    private val exerciseId: Long
) : ViewModel() {

    private val _training: MutableStateFlow<Training?> = MutableStateFlow(null)
    private val _exercise: MutableStateFlow<Exercise?> = MutableStateFlow(null)

    val state = combine(_training, _exercise) { training, exercise ->
        ExerciseAtWorkState(
            training = training,
            exercise = exercise,
            exerciseRecord = Exercise(
                trainingHistoryId = trainingId,
                exerciseTemplateId = exerciseId,
                name = exercise?.name ?: "",
                group = exercise?.group ?: "",
                image = exercise?.image ?: "",
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000L),
        initialValue = ExerciseAtWorkState()
    )

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch(Dispatchers.IO) {
        trainingDataSource.getTrainingById(trainingId).collect { training ->
            _training.value = training
            _exercise.value = exerciseDatasource.getExerciseById(exerciseId).first()
        }
    }

    fun onEvent(event: ExerciseAtWorkEvent) {
        when (event) {
            is ExerciseAtWorkEvent.OnAddNewSet -> {

            }

            is ExerciseAtWorkEvent.OnTimerChanged -> {

            }

            ExerciseAtWorkEvent.OnTimerStart -> {

            }
        }
    }
}