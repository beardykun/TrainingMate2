package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import jp.mikhail.pankratov.trainingMate.core.asResId
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.ExerciseAtWorkEvent
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.reps
import maxrep.shared.generated.resources.select_reps
import maxrep.shared.generated.resources.select_weight
import maxrep.shared.generated.resources.weight

@Composable
fun InputFields(
    weight: TextFieldValue,
    reps: TextFieldValue,
    inputError: InputError?,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    focus: FocusManager,
    focusRequesterWeight: FocusRequester,
    onFocusChangedWeight: (FocusState) -> Unit,
    focusRequesterReps: FocusRequester,
    onFocusChangedReps: (FocusState) -> Unit
) {
    val weightError =
        if (inputError is InputError.InputErrorWeight) inputError.asResId().getString() else ""
    val repsError =
        if (inputError is InputError.InputErrorReps) inputError.asResId().getString() else ""
    Row(modifier = Modifier.fillMaxWidth()) {
        InputField(
            value = weight,
            placeholder = Res.string.select_weight.getString(),
            label = Res.string.weight.getString(),
            onValueChanged = { value ->
                onEvent(ExerciseAtWorkEvent.OnWeightChanged(newWeight = value))
            },
            keyboardType = KeyboardType.Decimal,
            isError = inputError is InputError.InputErrorWeight,
            errorText = weightError,
            keyboardActions = KeyboardActions(onDone = {
                focus.clearFocus()
            }),
            modifier = Modifier.weight(1f)
                .focusRequester(focusRequesterWeight)
                .onFocusChanged(onFocusChangedWeight)
        )
        Spacer(modifier = Modifier.padding(Dimens.Padding32))
        InputField(
            value = reps,
            placeholder = Res.string.select_reps.getString(),
            label = Res.string.reps.getString(),
            onValueChanged = { value ->
                onEvent(ExerciseAtWorkEvent.OnRepsChanged(newReps = value))
            },
            keyboardType = KeyboardType.Number,
            isError = inputError is InputError.InputErrorReps,
            errorText = repsError,
            keyboardActions = KeyboardActions(onDone = {
                focus.clearFocus()
            }),
            modifier = Modifier.weight(1f)
                .focusRequester(focusRequesterReps)
                .onFocusChanged(onFocusChangedReps)
        )
    }
}
