package jp.mikhail.pankratov.trainingMate.mainScreens.user.presentation

import androidx.compose.ui.graphics.Color
import com.aay.compose.barChart.model.BarParameters
import jp.mikhail.pankratov.trainingMate.core.domain.DatabaseContract
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.MuscleStrength
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class UserInfoViewModel(exerciseUseCaseProvider: ExerciseUseCaseProvider) : ViewModel() {
    private val exerciseColors = mapOf(
        "barbell curls" to Color(0xFF4CAF50),   // Green
        "barbell squat" to Color(0xFFFF5722),   // Deep Orange
        "bench press" to Color(0xFF2196F3),     // Blue
        "chin ups" to Color(0xFFFFEB3B),        // Yellow
        "lying triceps press" to Color(0xFF9C27B0), // Purple
        "close grip barbell press" to Color(0xFFF44336), // Red
        "barbell shoulder press" to Color(0xFF00BCD4)   // Cyan
    )

    private val _state = MutableStateFlow(UserInfoState())
    val state = _state.asStateFlow()
        .onStart {
            val strengthDefineExercises =
                exerciseUseCaseProvider.getStrengthDefineExercisesUseCase().invoke().first()
            val map = calculateRelativeStrength(strengthDefineExercises)
            val parameters = map.map {
                BarParameters(
                    dataName = "${it.key}: ${it.value}%",
                    data = listOf(it.value.toDouble()),
                    barColor = exerciseColors.get(key = it.key) ?: Color.Black
                )
            }
            _state.update {
                it.copy(strengthLevelParams = parameters)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UserInfoState()
        )

    /***
     * Calculate relative strength of each exercise
     * @param userStrength - list of strength defining exercises from user
     * @return Map<String, Int> - map of exercise name and relative strength
     */
    //TODO Make available baseline setup by user choice (not the strongest exercise as now)?
    private fun calculateRelativeStrength(userStrength: List<ExerciseLocal>?): Map<String, Int> {
        val normalizedStrength = mutableMapOf<String, Double>()
        var strongestMuscleValue = 0.0
        userStrength?.forEach { exercise ->
            if (exercise.bestLiftedWeight == 0.0) {
                normalizedStrength[exercise.name] = 0.0
                return@forEach
            }
            val muscleStrength = exercise.bestLiftedWeight / getCorrectMuscleRate(exercise)
            if (exercise.group == DatabaseContract.CHEST_GROUP) {
                strongestMuscleValue = muscleStrength
            }
            normalizedStrength[exercise.name] = muscleStrength
        }
        if (strongestMuscleValue == 0.0)
            strongestMuscleValue = normalizedStrength.values.maxOrNull() ?: 1.0

        val relativeStrengthPercentages = normalizedStrength.mapValues { (_, value) ->
            ((value / strongestMuscleValue) * 100).toInt()
        }
        return relativeStrengthPercentages
    }

    private fun getCorrectMuscleRate(exerciseLocal: ExerciseLocal): Double {
        val muscleStrength = MuscleStrength()
        return when (exerciseLocal.group) {
            DatabaseContract.BICEPS_GROUP -> muscleStrength.biceps
            DatabaseContract.TRICEPS_GROUP -> muscleStrength.triceps
            DatabaseContract.SHOULDERS_GROUP -> muscleStrength.shoulders
            DatabaseContract.BACK_GROUP -> {
                if (exerciseLocal.name == "deadlift") muscleStrength.lowerBack else muscleStrength.upperBack
            }

            DatabaseContract.CHEST_GROUP -> muscleStrength.chest
            DatabaseContract.LEGS_GROUP -> muscleStrength.legs
            else -> 0.0
        }
    }
}