package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes

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