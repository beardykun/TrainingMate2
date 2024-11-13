package jp.mikhail.pankratov.trainingMate.core.domain.commomUseCases

import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError

class ValidateNumericInputUseCase {

    fun validateFloat(float: String): InputError? {
        return if (float == "0.0" || float == "0") {
            InputError.InputErrorFloat.WEIGHT_CANT_BE_0
        } else if (float.contains(",")) {
            InputError.InputErrorFloat.USE_DOT_IN_WEIGHT
        } else if (float.isBlank()) {
            InputError.InputErrorFloat.EMPTY_WEIGHT
        } else {
            try {
                float.toDouble()
                return null
            } catch (e: NumberFormatException) {
                return InputError.InputErrorFloat.INVALID_FORMAT
            }
        }
    }

    fun validateFloatNullable(float: String): InputError? {
        return if (float == "0.0" || float == "0") {
            InputError.InputErrorFloat.WEIGHT_CANT_BE_0
        } else if (float.contains(",")) {
            InputError.InputErrorFloat.USE_DOT_IN_WEIGHT
        } else if (float.isBlank()) {
            null
        } else {
            try {
                float.toDouble()
                return null
            } catch (e: NumberFormatException) {
                return InputError.InputErrorFloat.INVALID_FORMAT
            }
        }
    }

    fun validateInt(int: String): InputError? {
        return if (int.isBlank()) {
            InputError.InputErrorInt.EMPTY_REPS
        } else if (int.contains(",") || int.contains(".")) {
            InputError.InputErrorInt.REPS_IS_FLOAT
        } else if (int == "0") {
            InputError.InputErrorInt.REPS_CANT_BE_0
        } else {
            try {
                int.toInt()
                return null
            } catch (e: NumberFormatException) {
                return InputError.InputErrorInt.INVALID_FORMAT
            }
        }
    }

    fun validateIntNullable(int: String): InputError? {
        return if (int.isBlank()) {
            null
        } else if (int.contains(",") || int.contains(".")) {
            InputError.InputErrorInt.REPS_IS_FLOAT
        } else if (int == "0") {
            InputError.InputErrorInt.REPS_CANT_BE_0
        } else {
            try {
                int.toInt()
                return null
            } catch (e: NumberFormatException) {
                return InputError.InputErrorInt.INVALID_FORMAT
            }
        }
    }
}