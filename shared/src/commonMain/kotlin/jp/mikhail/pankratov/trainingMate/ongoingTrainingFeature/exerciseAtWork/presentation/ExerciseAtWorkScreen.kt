package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases.AutoInputMode
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables.AnimatedTextItem
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables.CountdownAnimation
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables.DifficultySelection
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables.ExerciseComparison
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.composables.InputFields
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.state.ExerciseAtWorkState
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.add_set
import maxrep.shared.generated.resources.auto_input
import maxrep.shared.generated.resources.break_time
import maxrep.shared.generated.resources.cd_start_timer
import maxrep.shared.generated.resources.delete_set
import maxrep.shared.generated.resources.hint_hard
import maxrep.shared.generated.resources.hint_light
import maxrep.shared.generated.resources.hint_medium
import maxrep.shared.generated.resources.sure_delete_set
import moe.tlaster.precompose.navigation.Navigator

const val COLUMNS_NUM = 3
private val COUNTDOWN_ANIMATION_RANGE = 0..10

@OptIn(ExperimentalMaterial3Api::class)
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
    LaunchedEffect(key1 = state.exerciseSettings) {
        onEvent(ExerciseAtWorkEvent.OnRefreshAutoInputValues)
    }
    val focus = LocalFocusManager.current
    Scaffold(
        topBar =
        {
            TopAppBar(
                title = {
                    TextLarge(
                        text = Routs.ExerciseScreens.exerciseAtWork,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navigator.navigate(route = "${Routs.ExerciseScreens.exerciseSettings}/${state.ongoingTraining?.trainingTemplateId}/${state.exerciseDetails.exerciseLocal?.id}")
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.goBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = Modifier.padding(all = Dimens.Padding16)
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            state.exerciseDetails.exercise?.name?.let {
                TextLarge(
                    text = it.uppercase(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
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
                    TextMedium(text = Res.string.auto_input.getString())
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
                SetDifficulty.Light -> Res.string.hint_light.getString()
                SetDifficulty.Medium -> Res.string.hint_medium.getString()
                SetDifficulty.Hard -> Res.string.hint_hard.getString()
            }
            TextSmall(hint, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            state.exerciseDetails.exercise?.sets?.let { sets ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = COLUMNS_NUM), modifier = Modifier.weight(1f)
                ) {
                    items(sets, key = { it.id }) { item ->
                        AnimatedTextItem(
                            lastTrainingSet = state.exerciseDetails.lastSameExercise?.sets?.getOrNull(
                                sets.indexOf(item)
                            ),
                            set = item,
                            onEvent = onEvent,
                            isAnimating = state.uiState.isAnimating,
                            isUsingTwoDumbbells = state.exerciseDetails.exerciseLocal?.usesTwoDumbbells
                                ?: false,
                            modifier = Modifier
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
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
                OutlinedButton(
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = Dimens.cardElevation),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    onClick = {
                        onEvent(ExerciseAtWorkEvent.OnAddNewSet)
                    },
                    modifier = Modifier.padding(bottom = Dimens.Padding12).size(Dimens.roundButton)
                ) {
                    TextMedium(text = Res.string.add_set.getString())
                }
                Spacer(modifier = Modifier.width(Dimens.Padding16))

                val timerImage =
                    if (state.timerState.isCounting) Icons.Default.TimerOff else Icons.Default.Timer
                Image(
                    imageVector = timerImage,
                    contentDescription = Res.string.cd_start_timer.getString(),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.clip(CircleShape)
                        .clickable {
                            if (state.timerState.isCounting)
                                onEvent(ExerciseAtWorkEvent.OnTimerStop)
                            else
                                onEvent(ExerciseAtWorkEvent.OnTimerStart)
                        }
                        .size(Dimens.timerIcon)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                )
            }

            AnimatedVisibility(visible = state.uiState.isDeleteDialogVisible) {
                DialogPopup(
                    title = Res.string.delete_set.getString(),
                    description = Res.string.sure_delete_set.getString(),
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
                    dialogTitle = Res.string.break_time.getString(),
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








