package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.TimerSpinnerWheels
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.no
import maxrep.shared.generated.resources.ok
import maxrep.shared.generated.resources.yes

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
                TextMedium(Res.string.yes.getString())
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDenny.invoke()
            }) {
                TextMedium(Res.string.no.getString())
            }
        })
}

@Composable
fun TimerDialog(
    minuteValue: Int,
    secondValue: Int,
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
                    minuteValue = minuteValue,
                    secondValue = secondValue,
                    onMinuteSelected = onMinuteUpdated,
                    onSecondSelected = onSecondUpdated
                )
            },
            confirmButton = {
                Button(onClick = { onConfirm.invoke() }) {
                    Text(text = Res.string.ok.getString())
                }
            }
        )
    }
}
