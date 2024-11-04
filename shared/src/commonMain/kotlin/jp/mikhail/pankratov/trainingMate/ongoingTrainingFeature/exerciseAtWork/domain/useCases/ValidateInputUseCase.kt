package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError
import jp.mikhail.pankratov.trainingMate.core.domain.util.Result
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state.ExerciseDetails

class ValidateInputUseCase {

    operator fun invoke(exerciseDetails: ExerciseDetails): InputError? {

        when (val weightError = validateWeight(exerciseDetails.weight.text)) {
            is Result.Error -> {
                return weightError.error
            }

            is Result.Success -> {}
        }
        when (val repsError = validateReps(exerciseDetails.reps.text)) {
            is Result.Error -> {
                return repsError.error
            }

            is Result.Success -> {}
        }
        return null
    }

    private fun validateWeight(weight: String): Result<Unit, InputError> {
        try {
            weight.toDouble()
        } catch (e: NumberFormatException) {
            return Result.Error(InputError.InputErrorWeight.INVALID_FORMAT)
        }

        return if (weight == "0.0" || weight == "0") {
            Result.Error(InputError.InputErrorWeight.WEIGHT_CANT_BE_0)
        } else if (weight.contains(",")) {
            Result.Error(InputError.InputErrorWeight.USE_DOT_IN_WEIGHT)
        } else if (weight.isBlank()) {
            Result.Error(InputError.InputErrorWeight.EMPTY_WEIGHT)
        } else Result.Success(Unit)
    }

    private fun validateReps(reps: String): Result<Unit, InputError> {
        try {
            reps.toInt()
        } catch (e: NumberFormatException) {
            return Result.Error(InputError.InputErrorReps.INVALID_FORMAT)
        }
        return if (reps.isBlank()) {
            Result.Error(InputError.InputErrorReps.EMPTY_REPS)
        } else if (reps.contains(",") || reps.contains(".")) {
            Result.Error(InputError.InputErrorReps.REPS_IS_FLOAT)
        } else if (reps == "0") {
            Result.Error(InputError.InputErrorReps.REPS_CANT_BE_0)
        } else Result.Success(Unit)
    }
}