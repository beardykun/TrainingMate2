package jp.mikhail.pankratov.trainingMate.exercise.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DropDown
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ExerciseAtWorkScreen(
    state: ExerciseAtWorkState,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ExerciseAtWorkEvent.OnTimerStart)
                }
            ) {
                Icon(imageVector = Icons.Default.Timer, contentDescription = "Start timer button")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                InputField(
                    value = state.weight,
                    placeholder = "Select weight",
                    label = "Weight",
                    onValueChanged = { value ->
                        onEvent(ExerciseAtWorkEvent.OnWeightChanged(newWeight = value))
                    },
                    keyboardType = KeyboardType.Decimal,
                    isError = state.errorWeight != null,
                    errorText = state.errorWeight,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(32.dp))
                InputField(
                    value = state.reps,
                    placeholder = "Select reps",
                    label = "Reps",
                    onValueChanged = { value ->
                        onEvent(ExerciseAtWorkEvent.OnRepsChanged(newReps = value))
                    },
                    keyboardType = KeyboardType.Number,
                    isError = state.errorReps != null,
                    errorText = state.errorReps,
                    modifier = Modifier.weight(1f)
                )
            }
            state.sets.let { sets ->
                LazyVerticalGrid(columns = GridCells.Fixed(count = 4)) {
                    items(sets) {
                        Text(it)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth()) {
                DropDown(
                    initValue = state.timer.toString(),
                    isOpen = state.isExpanded,
                    onClick = { onEvent(ExerciseAtWorkEvent.OnDropdownOpen) },
                    onDismiss = { onEvent(ExerciseAtWorkEvent.OnDropdownClosed) },
                    onSelectedValue = { value ->
                        onEvent(ExerciseAtWorkEvent.OnDropdownItemSelected(value))
                    },
                    values = listOf("15", "30", "45", "60", "90", "120", "150", "180", "300"),
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
                )
                Button(onClick = {
                    onEvent(ExerciseAtWorkEvent.OnAddNewSet)
                }, modifier = Modifier.fillMaxWidth()) {
                    TextLarge(text = "Add set")
                }
            }
        }
    }
}