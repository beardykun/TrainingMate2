package jp.mikhail.pankratov.trainingMate.createTraining.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.domain.Constants
import jp.mikhail.pankratov.trainingMate.core.domain.ToastManager
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonButton
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.GlobalToastMessage
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.InputField
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroupItem
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableGroupVertical
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.add_training
import maxrep.shared.generated.resources.choose_training_description
import maxrep.shared.generated.resources.choose_training_name
import maxrep.shared.generated.resources.invalid_or_duplicate_training_name
import maxrep.shared.generated.resources.invalid_training_description
import maxrep.shared.generated.resources.select_muscle_group
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource

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
            label = stringResource(Res.string.choose_training_name),
            placeholder = stringResource(Res.string.choose_training_name),
            modifier = Modifier.fillMaxWidth(),
            isError = state.invalidNameInput,
            errorText = if (state.invalidNameInput) stringResource(Res.string.invalid_or_duplicate_training_name) else ""
        )
        InputField(
            value = state.trainingDescription,
            onValueChanged = { newValue ->
                onEvent(CreateTrainingEvent.OnTrainingDescriptionChanged(description = newValue))
            },
            label = stringResource(Res.string.choose_training_description),
            placeholder = stringResource(Res.string.choose_training_description),
            modifier = Modifier.fillMaxWidth(),
            isError = state.invalidDescriptionInput,
            errorText = if (state.invalidDescriptionInput) stringResource(Res.string.invalid_training_description) else ""
        )


        SelectableGroupVertical(
            items = Constants.GROUPS,
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
        val groupError = Res.string.select_muscle_group.getString()
        CommonButton(
            onClick = {
                if (state.selectedGroups.isEmpty()) {
                    ToastManager.showToast(message = groupError)
                    return@CommonButton
                }
                onEvent(CreateTrainingEvent.OnAddNewTraining(onSuccess = {
                    navigator.navigate(Routs.TrainingScreens.selectTraining)
                }))
            },
            text = Res.string.add_training.getString()
        )
    }
    GlobalToastMessage()
}