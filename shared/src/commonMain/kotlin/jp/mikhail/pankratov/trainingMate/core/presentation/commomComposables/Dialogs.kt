package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.TimerSpinnerWheels

@Composable
fun DialogPopup(title: String, description: String, onAccept: () -> Unit, onDenny: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDenny.invoke()
        },
        title = { TextMedium(text = title) },
        text = { TextMedium(text = description) },
        confirmButton = {
            TextButton(onClick = {
                onAccept.invoke()
            }) {
                TextMedium(stringResource(SharedRes.strings.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDenny.invoke()
            }) {
                TextMedium(stringResource(SharedRes.strings.no))
            }
        })
}

@Composable
fun TimerDialog(
    dialogTitle: String,
    showDialog: Boolean,
    onMinuteUpdated: (Int) -> Unit,
    onSecondUpdated: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(dialogTitle) },
            text = {
                TimerSpinnerWheels(
                    onMinuteSelected = onMinuteUpdated,
                    onSecondSelected = onSecondUpdated
                )
            },
            confirmButton = {
                Button(onClick = { onConfirm.invoke() }) {
                    Text(text = "Res.string.apply")
                }
            }/*,
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }*/
        )
    }
}
