package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroups
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextMedium
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun CreateTrainingScreen(
    state: CreateTrainingState,
    onEvent: (CreateTrainingEvent) -> Unit,
    navigator: Navigator
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.Padding16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(
            value = state.trainingName,
            onValueChanged = { newValue ->
                onEvent(CreateTrainingEvent.OnTrainingNameChanged(name = newValue))
            },
            label = stringResource(SharedRes.strings.choose_training_name),
            placeholder = stringResource(SharedRes.strings.choose_training_name),
            modifier = Modifier.fillMaxWidth(),
            isError = state.invalidNameInput,
            errorText = if (state.invalidNameInput) stringResource(SharedRes.strings.invalid_or_duplicate_training_name) else ""
        )

        SelectableGroups(
            groups = state.trainingGroups,
            selected = state.selectedGroups
        ) { selectedGroup ->
            onEvent(CreateTrainingEvent.OnTrainingGroupsChanged(selectedGroup))
        }

        Button(
            onClick = {
                onEvent(CreateTrainingEvent.OnAddNewTraining(onSuccess = {
                    navigator.popBackStack()
                }))
            },
        ) {
            TextMedium(text = stringResource(SharedRes.strings.add_training))
        }
    }
}