package jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables

import Dimens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DropDown(
    initValue: String,
    values: List<String>,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectedValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onDismiss
        ) {
            values.forEach { value ->
                DropDownItem(
                    model = value,
                    onClick = { onSelectedValue.invoke(value) },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        Row(
            modifier = modifier
                .clickable(onClick = onClick)
                .padding(Dimens.Padding16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextMedium(
                text = initValue
            )
            Icon(
                imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isOpen) {
                    "SharedRes.strings.cd_close.localize()"
                } else {
                    "SharedRes.strings.ct_open.localize()"
                },
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun DropDownItem(
    model: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        onClick = onClick,
        modifier = modifier,
        text = { TextMedium(text = model) }
    )
}