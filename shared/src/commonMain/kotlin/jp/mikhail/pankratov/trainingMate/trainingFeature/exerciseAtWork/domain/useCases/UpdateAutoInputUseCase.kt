package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases

import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.AutoInputMode.*
import kotlin.math.roundToInt

private const val REST_REPS_INCREMENT = 1.5
class UpdateAutoInputUseCase {
    operator fun invoke(
        exerciseLocal: ExerciseLocal?,
        currentSets: List<ExerciseSet>,
        pastSets: List<ExerciseSet>,
        autoInputMode: AutoInputMode
    ): AutoInputResult? {
        var counter = currentSets.size
        if (counter >= pastSets.size) {
            counter = pastSets.size - 1
        }

        val pastSet = pastSets.getOrNull(counter)
        val increment = when (pastSet?.difficulty) {
            SetDifficulty.Hard -> 0.0
            else -> if (exerciseLocal?.usesTwoDumbbells == true) {
                2.0
            } else {
                2.5
            }
        }
        val oldWeight = pastSet?.weight?.toDouble() ?: 0.0
        val oldReps = pastSet?.reps?.toInt() ?: 0

        val autoInputResult = when (autoInputMode) {
            NONE -> null
            REST -> {
                val newReps = (oldReps * REST_REPS_INCREMENT).toInt()
                var newWeight = oldWeight * oldReps / newReps
                val incrementUnit = if (exerciseLocal?.usesTwoDumbbells == true) 2.0 else 2.5
                newWeight = (newWeight / incrementUnit).roundToInt() * incrementUnit
                AutoInputResult(
                    reps = TextFieldValue(newReps.toString()),
                    weight = TextFieldValue(newWeight.toString())
                )
            }

            SAME -> {
                AutoInputResult(
                    reps = TextFieldValue(oldReps.toString()),
                    weight = TextFieldValue(oldWeight.toString())
                )
            }

            PROGRESS -> {
                val newWeight = oldWeight.plus(increment)
                AutoInputResult(
                    reps = TextFieldValue(oldReps.toString()),
                    weight = TextFieldValue(newWeight.toString())
                )
            }
        }
        return autoInputResult
    }
}

data class AutoInputResult(
    val weight: TextFieldValue,
    val reps: TextFieldValue
)

enum class AutoInputMode {
    NONE,
    REST,
    SAME,
    PROGRESS
}