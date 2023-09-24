package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroups
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun CreateTraining(
    state: CreateTrainingState,
    onEvent: (CreateTrainingEvent) -> Unit,
    navigator: Navigator
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.Padding16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.trainingName,
            onValueChange = { newValue ->
                onEvent(CreateTrainingEvent.OnTrainingNameChanged(name = newValue))
            },
            label = { Text(text = "Choose a training name") },
            singleLine = true
        )

        SelectableGroups(
            groups = state.trainingGroups,
            isSelected = state.selectedGroups
        ) { selectedGroup ->
            onEvent(CreateTrainingEvent.OnTrainingGroupsChanged(selectedGroup))
        }

        Button(
            onClick = {
               onEvent(CreateTrainingEvent.OnAddNewTraining)
                navigator.popBackStack()
            },
        ) {
            TextMedium(text = "Add Training")
        }
    }
}