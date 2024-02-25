package jp.mikhail.pankratov.trainingMate.addExercises.presentation

import Dimens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.DialogPopup
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.SelectableExercises
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun AddExercisesScreen(
    state: AddExercisesState,
    onEvent: (AddExercisesEvent) -> Unit,
    navigator: Navigator
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navigator.navigate(Routs.TrainingScreens.createExercise)
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create new exercises button"
            )
        }
    }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.Padding16, vertical = Dimens.Padding8)
        ) {
            state.availableExerciseLocals?.let { exercises ->
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
            }
            Button(
                onClick = {
                    onEvent(AddExercisesEvent.OnAddNewExercises {
                        navigator.popBackStack()
                    })
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add exercises")
            }
            AnimatedVisibility(visible = state.isDeleteDialogVisible) {
                DialogPopup(
                    title = stringResource(SharedRes.strings.delete_set),
                    description = stringResource(SharedRes.strings.sure_delete_set),
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
}