package jp.mikhail.pankratov.trainingMate.core.domain.util

import jp.mikhail.pankratov.trainingMate.core.domain.util.Error


sealed interface InputError: Error {
    enum class InputErrorReps : InputError {
        INVALID_FORMAT,
        EMPTY_REPS,
        REPS_IS_FLOAT,
        REPS_CANT_BE_0
    }

    enum class InputErrorWeight : InputError {
        INVALID_FORMAT,
        WEIGHT_CANT_BE_0,
        USE_DOT_IN_WEIGHT,
        EMPTY_WEIGHT
    }
}



