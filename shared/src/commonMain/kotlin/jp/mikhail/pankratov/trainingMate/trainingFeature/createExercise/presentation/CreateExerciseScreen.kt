package jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.presentation

import Dimens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonButton
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroupItem
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroupVertical
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import jp.mikhail.pankratov.trainingMate.core.stringToList
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.add_exercise
import maxrep.shared.generated.resources.choose_exercise_name
import maxrep.shared.generated.resources.invalid_or_duplicate_exercise_name
import maxrep.shared.generated.resources.using_two_dumbbell
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun CreateExerciseScreen(
    state: CreateExerciseState,
    onEvent: (CreateExerciseEvent) -> Unit,
    navigator: Navigator
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.Padding16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        InputField(
            value = state.exerciseName,
            onValueChanged = { newValue ->
                onEvent(CreateExerciseEvent.OnExerciseNameChanged(newName = newValue))
            },
            label = Res.string.choose_exercise_name.getString(),
            placeholder = Res.string.choose_exercise_name.getString(),
            modifier = Modifier.fillMaxWidth(),
            isError = state.invalidNameInput,
            errorText = if (state.invalidNameInput) Res.string.invalid_or_duplicate_exercise_name.getString() else ""
        )

        state.ongoingTraining?.let { ongoingTraining ->
            val groups = ongoingTraining.groups.stringToList()
            if (groups.size == 1) {
                LaunchedEffect(Unit) {
                    onEvent(CreateExerciseEvent.OnExerciseGroupChanged(groups.first()))
                }
            } else {
                val selectedGroups = state.exerciseGroup.stringToList()
                SelectableGroupVertical(
                    items = groups,
                    selected = selectedGroups,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedGroup ->
                        onEvent(CreateExerciseEvent.OnExerciseGroupChanged(selectedGroup))
                    },
                    displayItem = { it },
                    listItem = { item, isSelected, onClick, modifier ->
                        SelectableGroupItem(
                            group = item,
                            isSelected = isSelected,
                            onClick = onClick,
                            modifier = modifier
                        )
                    }
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = state.usesTwoDumbbell, onCheckedChange = {
                onEvent(CreateExerciseEvent.OnExerciseUsesTwoDumbbells)
            })
            TextMedium(text = Res.string.using_two_dumbbell.getString())
        }
        CommonButton(
            onClick = {
                onEvent(CreateExerciseEvent.OnExerciseCreate(onSuccess = {
                    navigator.navigate(Routs.TrainingScreens.addExercises)
                }))
            },
            text = Res.string.add_exercise.getString()
        )
    }
}