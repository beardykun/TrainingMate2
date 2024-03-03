package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes

const val REPS_ERROR_1 = "r1"
const val REPS_ERROR_2 = "r2"
const val REPS_ERROR_3 = "r3"
const val WEIGHT_ERROR_1 = "w1"
const val WEIGHT_ERROR_2 = "w2"
const val WEIGHT_ERROR_3 = "w3"
const val WEIGHT_ERROR_4 = "w4"

@Composable
fun getErrorMessage(errorCode: String?): String {
    return when (errorCode) {
        WEIGHT_ERROR_1 -> stringResource(SharedRes.strings.invalid_input_format)
        WEIGHT_ERROR_2 -> stringResource(SharedRes.strings.weight_cant_be_0)
        WEIGHT_ERROR_3 -> stringResource(SharedRes.strings.use_dot)
        WEIGHT_ERROR_4 -> stringResource(SharedRes.strings.weight_should_not_be_empty)
        REPS_ERROR_1 -> stringResource(SharedRes.strings.reps_should_not_be_empty)
        REPS_ERROR_2 -> stringResource(SharedRes.strings.not_a_float)
        REPS_ERROR_3 -> stringResource(SharedRes.strings.not_a_0)
        else -> ""
    }
}