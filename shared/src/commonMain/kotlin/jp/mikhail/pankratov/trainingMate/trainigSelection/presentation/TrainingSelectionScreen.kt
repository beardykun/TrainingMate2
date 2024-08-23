package jp.mikhail.pankratov.trainingMate.trainigSelection.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.LocalTrainingItem
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingScreenEvent
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrainingSelectionScreen(
    state: TrainingSelectionState,
    onEvent: (TrainingSelectionEvent) -> Unit,
    navigator: Navigator
) {
    state.availableTrainings?.let { trainings ->
        Column {
            TextLarge(text = stringResource(SharedRes.strings.start_new_training).uppercase())
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(trainings,
                    key = { item ->
                        item.name
                    }
                ) { training ->
                    LocalTrainingItem(
                        training = training,
                        onClick = {
                            onEvent(
                                TrainingSelectionEvent.OnTrainingItemClick(
                                    shouldShowDialog = true,
                                    training = training
                                )
                            )
                        },
                        onDeleteClick = { id ->
                            onEvent(TrainingSelectionEvent.OnTrainingTemplateDelete(id))
                        },
                        modifier = Modifier.animateItemPlacement(),
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
            }
            AnimatedVisibility(visible = state.showDeleteTemplateDialog) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.delete_training),
                    description = stringResource(SharedRes.strings.want_to_delete_training),
                    onAccept = {
                        onEvent(TrainingSelectionEvent.OnDeleteTemplateConfirmClick)
                    },
                    onDenny = {
                        onEvent(TrainingSelectionEvent.OnDeleteTemplateDenyClick)
                    }
                )
            }

            AnimatedVisibility(visible = state.showStartTrainingDialog) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.start_training),
                    description = stringResource(SharedRes.strings.start_new_training),
                    onAccept = {
                        onEvent(TrainingSelectionEvent.OnStartNewTraining)
                        navigator.navigate(Routs.TrainingScreens.trainingExercises)
                    },
                    onDenny = {
                        onEvent(TrainingSelectionEvent.OnStartNewTrainingDeny)
                    }
                )
            }
        }
    }
}