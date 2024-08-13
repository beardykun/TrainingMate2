package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.GlobalToastMessage
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroupHorizontal
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TimerDialog
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.AutoInputMode
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.AnimatedTextItem
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.CountdownAnimation
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.DifficultySelection
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.ExerciseComparison
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.InputFields
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.state.ExerciseAtWorkState
import moe.tlaster.precompose.navigation.Navigator

const val COLUMNS_NUM = 3
private val COUNTDOWN_ANIMATION_RANGE = 0..10

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
    Scaffold(modifier = Modifier.padding(all = Dimens.Padding16)) { padding ->
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
                ) { exerciseName ->
                    navigator.navigate("${Routs.ExerciseScreens.exerciseAtWorkHistory}/$exerciseName")
                }
            }
            AnimatedVisibility(visible = state.exerciseDetails.lastSameExercise?.sets?.isEmpty() == false) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextMedium(text = SharedRes.strings.auto_input.getString())
                    SelectableGroupHorizontal(
                        items = AutoInputMode.entries.minus(AutoInputMode.NONE),
                        selected = state.uiState.autoInputSelected,
                        onClick = { autoInputMode ->
                            onEvent(ExerciseAtWorkEvent.OnAutoInputChanged(autoInputMode))
                        },
                        displayItem = { it.name },
                        listItem = { item, selected, onClick ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextSmall(text = item.name)
                                Checkbox(
                                    checked = selected == item,
                                    onCheckedChange = {
                                        onClick.invoke(item)
                                    })
                            }
                        }
                    )
                }
            }

            InputFields(
                weight = state.exerciseDetails.weight,
                reps = state.exerciseDetails.reps,
                inputError = state.exerciseDetails.inputError,
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
                    items(sets, key = { it.id }) { item ->
                        AnimatedTextItem(
                            lastTrainingSet = state.exerciseDetails.lastSameExercise?.sets?.getOrNull(
                                sets.indexOf(item)
                            ),
                            set = item,
                            onEvent = onEvent,
                            isAnimating = state.uiState.isAnimating,
                            modifier = Modifier
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth().height(Dimens.timerIcon),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val minText =
                    if (state.timerState.timerMin < 10) "0${state.timerState.timerMin}" else state.timerState.timerMin
                val secText =
                    if (state.timerState.timerSec < 10) "0${state.timerState.timerSec}" else state.timerState.timerSec
                TextLarge(
                    textAlign = TextAlign.Center,
                    text = "$minText:$secText",
                    modifier = Modifier.clickable {
                        onEvent(ExerciseAtWorkEvent.OnDropdownOpen)
                    }.clip(
                        RoundedCornerShape(percent = 50)
                    ).background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(Dimens.Padding16)
                )

                Spacer(modifier = Modifier.width(Dimens.Padding16))
                Button(onClick = {
                    onEvent(ExerciseAtWorkEvent.OnAddNewSet)
                }, modifier = Modifier.fillMaxSize().weight(1f)) {
                    TextMedium(text = stringResource(SharedRes.strings.add_set))
                }
                Spacer(modifier = Modifier.width(Dimens.Padding16))

                val timerImage =
                    if (state.timerState.isCounting) Icons.Default.TimerOff else Icons.Default.Timer
                Image(
                    imageVector = timerImage,
                    contentDescription = stringResource(SharedRes.strings.cd_start_timer),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.clip(CircleShape)
                        .clickable {
                            if (state.timerState.isCounting)
                                onEvent(ExerciseAtWorkEvent.OnTimerStop)
                            else
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

            AnimatedVisibility(visible = state.timerState.isExpanded) {
                TimerDialog(
                    minuteValue = state.timerState.timerMin,
                    secondValue = state.timerState.timerSec,
                    dialogTitle = stringResource(SharedRes.strings.break_time),
                    onDismiss = { onEvent(ExerciseAtWorkEvent.OnDropdownClosed) },
                    onMinuteUpdated = { value -> onEvent(ExerciseAtWorkEvent.OnMinutesUpdated(value)) },
                    onSecondUpdated = { value -> onEvent(ExerciseAtWorkEvent.OnSecondsUpdated(value)) },
                    showDialog = state.timerState.isExpanded,
                    onConfirm = {
                        onEvent(ExerciseAtWorkEvent.OnDropdownItemSelected)
                    },
                )
            }
        }
        AnimatedVisibility(
            visible = state.timerState.isCounting &&
                    state.timerState.timerMin == 0 &&
                    state.timerState.timerSec in COUNTDOWN_ANIMATION_RANGE
        ) {
            if (state.timerState.timerSec in COUNTDOWN_ANIMATION_RANGE) {
                CountdownAnimation(currentTimerValue = state.timerState.timerSec)
            }
        }
        GlobalToastMessage()
    }
}








