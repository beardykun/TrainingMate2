package jp.mikhail.pankratov.trainingMate.exercise.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DropDown
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ExerciseAtWorkScreen(
    state: ExerciseAtWorkState,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold {
        Column(modifier = Modifier.fillMaxSize().padding(Dimens.Padding16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                InputField(
                    value = state.weight,
                    placeholder = stringResource(SharedRes.strings.select_weight),
                    label = stringResource(SharedRes.strings.weight),
                    onValueChanged = { value ->
                        onEvent(ExerciseAtWorkEvent.OnWeightChanged(newWeight = value))
                    },
                    keyboardType = KeyboardType.Decimal,
                    isError = state.errorWeight != null,
                    errorText = getErrorMessage(state.errorWeight),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(Dimens.Padding32.dp))
                InputField(
                    value = state.reps,
                    placeholder = stringResource(SharedRes.strings.select_reps),
                    label = stringResource(SharedRes.strings.reps),
                    onValueChanged = { value ->
                        onEvent(ExerciseAtWorkEvent.OnRepsChanged(newReps = value))
                    },
                    keyboardType = KeyboardType.Number,
                    isError = state.errorReps != null,
                    errorText = getErrorMessage(state.errorReps),
                    modifier = Modifier.weight(1f)
                )
            }
            state.exercise?.sets?.let { sets ->
                LazyVerticalGrid(columns = GridCells.Fixed(count = 3)) {
                    items(sets) { item ->
                        Text(item, modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                onEvent(ExerciseAtWorkEvent.OnDisplayDeleteDialog(true, item))
                            })
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                DropDown(
                    initValue = state.timer.toString(),
                    isOpen = state.isExpanded,
                    onClick = { onEvent(ExerciseAtWorkEvent.OnDropdownOpen) },
                    onDismiss = { onEvent(ExerciseAtWorkEvent.OnDropdownClosed) },
                    onSelectedValue = { value ->
                        onEvent(ExerciseAtWorkEvent.OnDropdownItemSelected(value))
                    },
                    values = listOf("15", "30", "45", "60", "90", "120", "150", "180", "300"),
                    modifier = Modifier.clip(
                        RoundedCornerShape(percent = 50)
                    ).background(color = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(Dimens.Padding16.dp))
                Button(onClick = {
                    onEvent(ExerciseAtWorkEvent.OnAddNewSet)
                }, modifier = Modifier.fillMaxSize().weight(1f)) {
                    TextMedium(text = stringResource(SharedRes.strings.add_set))
                }
                Spacer(modifier = Modifier.width(Dimens.Padding16.dp))
                Image(
                    imageVector = Icons.Default.Timer,
                    contentDescription = "Start timer",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.clickable {
                        onEvent(ExerciseAtWorkEvent.OnTimerStart)
                    }.fillMaxHeight()
                        .width(60.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                )
            }
            AnimatedVisibility(visible = state.isDeleteDialogVisible) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.delete_set),
                    description = stringResource(SharedRes.strings.sure_delete_set),
                    onAccept = {
                        onEvent(ExerciseAtWorkEvent.OnSetDelete)
                    },
                    onDenny = {
                        onEvent(ExerciseAtWorkEvent.OnDisplayDeleteDialog(false))
                    }
                )
            }
        }
    }
}