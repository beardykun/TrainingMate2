package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases

import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty

class UpdateAutoInputUseCase {
    operator fun invoke(
        exerciseLocal: ExerciseLocal?,
        currentSets: List<ExerciseSet>,
        pastSets: List<ExerciseSet>
    ): AutoInputResult {
        var counter = currentSets.size
        if (counter >= pastSets.size) {
            counter = currentSets.size - 1
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

        val newWeight = pastSet?.weight?.toDouble()?.plus(increment).toString()
        val newReps = pastSet?.reps ?: ""

        return AutoInputResult(
            weight = TextFieldValue(newWeight),
            reps = TextFieldValue(newReps)
        )
    }
}

data class AutoInputResult(
    val weight: TextFieldValue,
    val reps: TextFieldValue
)