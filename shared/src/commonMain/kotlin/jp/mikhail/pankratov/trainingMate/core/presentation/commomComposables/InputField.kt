package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun InputField(
    value: TextFieldValue,
    label: String,
    placeholder: String,
    onValueChanged: (TextFieldValue) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorText: String? = null,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions(),
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChanged.invoke(
                newValue.copy(
                    selection = TextRange(newValue.text.length)
                )
            )
        },
        shape = MaterialTheme.shapes.large,
        label = {
            TextMedium(
                label,
            )
        },
        placeholder = {
            TextMedium(
                text = placeholder
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType).copy(
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
        ),
        supportingText = { TextMedium(errorText ?: "") },
        isError = isError,
        modifier = modifier
            .padding(top = Dimens.Padding16)
    )
}