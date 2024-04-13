package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DropDown
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.AnimatedTextSizeItem
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.DifficultySelection
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state.ExerciseAtWorkState
import moe.tlaster.precompose.navigation.Navigator

const val COLUMNS_NUM = 3

//Use same weights as before, increase or decrease selection
@Composable
fun ExerciseAtWorkScreen(
    state: ExerciseAtWorkState,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    navigator: Navigator
) {

    val focusRequesterWeight = remember { FocusRequester() }
    val focusRequesterReps = remember { FocusRequester() }

    // Handle the focus event
    val onFocusChangedReps: (FocusState) -> Unit = { focusState ->
        if (focusState.isFocused) {
            // Clear the input when the field is focused
            onEvent(ExerciseAtWorkEvent.OnRepsChanged(newReps = TextFieldValue("")))
        }
    }
    val onFocusChangedWeight: (FocusState) -> Unit = { focusState ->
        if (focusState.isFocused) {
            // Clear the input when the field is focused
            onEvent(ExerciseAtWorkEvent.OnWeightChanged(newWeight = TextFieldValue("")))
        }
    }
    val focus = LocalFocusManager.current
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navigator.navigate("${Routs.ExerciseScreens.exerciseAtWorkHistory}/${state.exerciseDetails.exercise?.name}")
            },
            modifier = Modifier.padding(bottom = Dimens.historyFabPadding)
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = stringResource(SharedRes.strings.cd_exercise_history)
            )
        }
    }, modifier = Modifier.padding(all = Dimens.Padding16)) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            state.exerciseDetails.exercise?.name?.let {
                TextLarge(
                    text = it.uppercase(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            if (state.exerciseDetails.exercise != null) {
                ExerciseComparison(
                    lastExercise = state.exerciseDetails.lastSameExercise,
                    exercise = state.exerciseDetails.exercise
                )
            }
            AnimatedVisibility(visible = state.exerciseDetails.lastSameExercise?.sets?.isEmpty() == false) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextMedium(text = SharedRes.strings.auto_input.getString())
                    Checkbox(
                        checked = state.uiState.isAutoInputEnabled,
                        onCheckedChange = { checkState ->
                            onEvent(ExerciseAtWorkEvent.OnAutoInputToggled(checkState))
                        })
                }
            }

            InputFields(
                weight = state.exerciseDetails.weight,
                reps = state.exerciseDetails.reps,
                errorWeight = state.exerciseDetails.errorWeight?.getString(),
                errorReps = state.exerciseDetails.errorReps?.getString(),
                onEvent = onEvent,
                focus = focus,
                focusRequesterWeight = focusRequesterWeight,
                onFocusChangedWeight = onFocusChangedWeight,
                focusRequesterReps = focusRequesterReps,
                onFocusChangedReps = onFocusChangedReps
            )
            DifficultySelection(
                selected = state.exerciseDetails.setDifficulty.name,
                onSelect = { difficulty ->
                    onEvent(ExerciseAtWorkEvent.OnSetDifficultySelected(difficulty))
                })
            val hint = when (state.exerciseDetails.setDifficulty) {
                SetDifficulty.Light -> SharedRes.strings.hint_light.getString()
                SetDifficulty.Medium -> SharedRes.strings.hint_medium.getString()
                SetDifficulty.Hard -> SharedRes.strings.hint_hard.getString()
            }
            TextSmall(hint, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            state.exerciseDetails.exercise?.sets?.let { sets ->
                LazyVerticalGrid(columns = GridCells.Fixed(count = COLUMNS_NUM)) {
                    items(sets) { item ->
                        AnimatedTextSizeItem(
                            set = item,
                            onEvent = onEvent,
                            isAnimating = state.uiState.isAnimating,
                            modifier = Modifier
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth().height(Dimens.timerIcon)) {
                DropDown(
                    initValue = state.timerState.timer.toString(),
                    isOpen = state.timerState.isExpanded,
                    onClick = { onEvent(ExerciseAtWorkEvent.OnDropdownOpen) },
                    onDismiss = { onEvent(ExerciseAtWorkEvent.OnDropdownClosed) },
                    onSelectedValue = { value ->
                        onEvent(ExerciseAtWorkEvent.OnDropdownItemSelected(value))
                    },
                    values = listOf("30", "45", "60", "90", "120", "150", "180", "300"),
                    modifier = Modifier.clip(
                        RoundedCornerShape(percent = 50)
                    ).background(color = MaterialTheme.colorScheme.primaryContainer)
                )
                Spacer(modifier = Modifier.width(Dimens.Padding16))
                Button(onClick = {
                    onEvent(ExerciseAtWorkEvent.OnAddNewSet)
                }, modifier = Modifier.fillMaxSize().weight(1f)) {
                    TextMedium(text = stringResource(SharedRes.strings.add_set))
                }
                Spacer(modifier = Modifier.width(Dimens.Padding16))

                Image(
                    imageVector = Icons.Default.Timer,
                    contentDescription = stringResource(SharedRes.strings.cd_start_timer),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.clip(CircleShape)
                        .clickable {
                            onEvent(ExerciseAtWorkEvent.OnTimerStart)
                        }
                        .fillMaxHeight()
                        .width(Dimens.timerIcon)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                )
            }

            AnimatedVisibility(visible = state.uiState.isDeleteDialogVisible) {
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
        AnimatedVisibility(visible = state.timerState.timer <= 10) {
            if (state.timerState.timer <= 10) {
                CountdownAnimation(currentTimerValue = state.timerState.timer)
            }
        }
    }
}

@Composable
fun InputFields(
    weight: TextFieldValue,
    reps: TextFieldValue,
    errorWeight: String?,
    errorReps: String?,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    focus: FocusManager,
    focusRequesterWeight: FocusRequester,
    onFocusChangedWeight: (FocusState) -> Unit,
    focusRequesterReps: FocusRequester,
    onFocusChangedReps: (FocusState) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        InputField(
            value = weight,
            placeholder = stringResource(SharedRes.strings.select_weight),
            label = stringResource(SharedRes.strings.weight),
            onValueChanged = { value ->
                onEvent(ExerciseAtWorkEvent.OnWeightChanged(newWeight = value))
            },
            keyboardType = KeyboardType.Decimal,
            isError = errorWeight != null,
            errorText = errorWeight,
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
            placeholder = stringResource(SharedRes.strings.select_reps),
            label = stringResource(SharedRes.strings.reps),
            onValueChanged = { value ->
                onEvent(ExerciseAtWorkEvent.OnRepsChanged(newReps = value))
            },
            keyboardType = KeyboardType.Number,
            isError = errorReps != null,
            errorText = errorReps,
            keyboardActions = KeyboardActions(onDone = {
                focus.clearFocus()
            }),
            modifier = Modifier.weight(1f)
                .focusRequester(focusRequesterReps)
                .onFocusChanged(onFocusChangedReps)
        )
    }
}

@Composable
fun CountdownAnimation(
    currentTimerValue: Int,
) {
    // This will hold the current scale of the animation.
    val scale: Float by animateFloatAsState(
        targetValue = 1f / (currentTimerValue * 0.1f),
        animationSpec = tween(
            durationMillis = 500, // duration of the animation
            easing = LinearEasing
        )
    )

    // We display the countdown text, making sure it's in the middle of the screen.
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = if (currentTimerValue > 0) currentTimerValue.toString() else "",
            fontSize = 120.sp, // or whatever size is appropriate
            modifier = Modifier.scale(scale) // applying the scale modifier
        )
    }
}

@Composable
fun ExerciseComparison(lastExercise: Exercise?, exercise: Exercise) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = Dimens.Padding16, start = Dimens.Padding16, end = Dimens.Padding16)
            .clip(RoundedCornerShape(percent = 30))
            .padding(vertical = Dimens.Padding8)

    ) {
        lastExercise?.let {
            Card(
                modifier = Modifier.weight(1f).padding(horizontal = Dimens.Padding8),
                elevation = CardDefaults.cardElevation(Dimens.cardElevation)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextLarge(
                        text =
                        stringResource(
                            SharedRes.strings.last_exercise,
                            lastExercise.totalLiftedWeight,
                            lastExercise.sets.size
                        ),
                        modifier = Modifier.padding(all = Dimens.Padding8)
                    )
                }
            }
        }

        Card(
            modifier = Modifier.weight(1f).padding(horizontal = Dimens.Padding8),
            elevation = CardDefaults.cardElevation(Dimens.cardElevation)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextLarge(
                    text =
                    stringResource(
                        SharedRes.strings.this_exercise,
                        exercise.totalLiftedWeight,
                        exercise.sets.size
                    ),
                    modifier = Modifier.padding(all = Dimens.Padding8)
                )
            }
        }
    }
}





