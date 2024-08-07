package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroupItem
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroupVertical
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

        SelectableGroupVertical(
            items = state.trainingGroups,
            selected = state.selectedGroups,
            displayItem = { it },
            onClick = { selectedGroup ->
                onEvent(CreateTrainingEvent.OnTrainingGroupsChanged(selectedGroup))
            },
            listItem = { item, isSelected, onClick, modifier ->
                SelectableGroupItem(
                    group = item,
                    isSelected = isSelected,
                    onClick = onClick,
                    modifier = modifier
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onEvent(CreateTrainingEvent.OnAddNewTraining(onSuccess = {
                    navigator.popBackStack()
                }))
            },
            modifier = Modifier.padding(vertical = Dimens.Padding16, horizontal = Dimens.Padding48)
        ) {
            TextMedium(text = stringResource(SharedRes.strings.add_training))
        }
    }
}