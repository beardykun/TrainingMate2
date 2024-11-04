package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonButton
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableExercises
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.apply_changes
import maxrep.shared.generated.resources.create_new_ex_btn
import maxrep.shared.generated.resources.delete_set
import maxrep.shared.generated.resources.sure_delete_set
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun AddExercisesScreen(
    state: AddExercisesState,
    onEvent: (AddExercisesEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navigator.navigate(Routs.TrainingScreens.createExercise)
            },
            modifier = Modifier.padding(bottom = Dimens.Padding64)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = Res.string.create_new_ex_btn.getString()
            )
        }
    }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.Padding16, vertical = Dimens.Padding8)
        ) {
            state.sortedExercises?.let { exercises ->
                SelectableExercises(
                    exerciseLocals = exercises,
                    isSelected = state.selectedExercises,
                    modifier = Modifier.weight(1f),
                    onClick = { exercise ->
                        onEvent(AddExercisesEvent.OnSelectExercise(exercise.name))
                    },
                    onDeleteClick = { exercise ->
                        onEvent(
                            AddExercisesEvent.OnDisplayDeleteDialog(
                                exercise = exercise,
                                isDeleteVisible = true
                            )
                        )
                    }
                )

                CommonButton(
                    onClick = {
                        onEvent(AddExercisesEvent.OnAddNewExercises {
                            navigator.navigate(Routs.TrainingScreens.trainingExercises)
                        })
                    },
                    text = Res.string.apply_changes.getString()
                )
            }
        }

        AnimatedVisibility(visible = state.isDeleteDialogVisible) {
            DialogPopup(
                title = Res.string.delete_set.getString(),
                description = Res.string.sure_delete_set.getString(),
                onAccept = {
                    onEvent(AddExercisesEvent.OnDeleteExercise)
                },
                onDenny = {
                    onEvent(AddExercisesEvent.OnDisplayDeleteDialog(false))
                }
            )
        }
    }
}