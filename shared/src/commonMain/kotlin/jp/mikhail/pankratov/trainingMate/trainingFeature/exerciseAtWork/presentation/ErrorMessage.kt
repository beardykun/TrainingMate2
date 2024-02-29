package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes

@Composable
fun getErrorMessage(errorCode: String?): String {
    return when (errorCode) {
        "w1" -> stringResource(SharedRes.strings.invalid_input_format)
        "w2" -> stringResource(SharedRes.strings.weight_cant_be_0)
        "w3" -> stringResource(SharedRes.strings.use_dot)
        "w4" -> stringResource(SharedRes.strings.weight_should_not_be_empty)
        "r1" -> stringResource(SharedRes.strings.reps_should_not_be_empty)
        "r2" -> stringResource(SharedRes.strings.not_a_float)
        "r3" -> stringResource(SharedRes.strings.not_a_0)
        else -> ""
    }
}