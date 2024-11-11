package jp.mikhail.pankratov.trainingMate.core.domain.util


sealed interface InputError: Error {
    enum class InputErrorInt : InputError {
        INVALID_FORMAT,
        EMPTY_REPS,
        REPS_IS_FLOAT,
        REPS_CANT_BE_0
    }

    enum class InputErrorFloat : InputError {
        INVALID_FORMAT,
        WEIGHT_CANT_BE_0,
        USE_DOT_IN_WEIGHT,
        EMPTY_WEIGHT
    }
}



