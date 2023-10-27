package jp.mikhail.pankratov.trainingMate.createExercise

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroups
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.stringToList
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun CreateExerciseScreen(
    state: CreateExerciseState,
    onEvent: (CreateExerciseEvent) -> Unit,
    navigator: Navigator
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.Padding16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(
            value = state.exerciseName,
            onValueChanged = { newValue ->
                onEvent(CreateExerciseEvent.OnExerciseNameChanged(newName = newValue))
            },
            label = "Choose a exercise name",
            placeholder = "Choose a exercise name",
            modifier = Modifier.fillMaxWidth(),
            isError = state.invalidNameInput,
            errorText = if (state.invalidNameInput) "Invalid or duplicate exercise name" else ""
        )

        state.ongoingTraining?.let { ongoingTraining ->
            SelectableGroups(
                groups = ongoingTraining.groups.stringToList(),
                selected = state.exerciseGroup.stringToList(),
                modifier = Modifier.weight(1f)
            ) { selectedGroup ->
                onEvent(CreateExerciseEvent.OnExerciseGroupChanged(selectedGroup))
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = state.usesTwoDumbbell, onCheckedChange = {
                onEvent(CreateExerciseEvent.OnExerciseUsesTwoDumbbells)
            })
            TextMedium(text = "Check the exercise you use two dumbbells but recording the weight of one dumbbell")
        }
        Spacer(modifier = Modifier.height(Dimens.Padding16.dp))
        Button(
            onClick = {
                onEvent(CreateExerciseEvent.OnExerciseCreate(onSuccess = {
                    navigator.popBackStack()
                }))
            },
        ) {
            TextMedium(text = "Add Exercise")
        }
    }
}