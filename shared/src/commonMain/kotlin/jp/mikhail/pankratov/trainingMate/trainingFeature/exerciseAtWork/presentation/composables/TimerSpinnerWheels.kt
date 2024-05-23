package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import Dimens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge

@Composable
fun TimerSpinnerWheels(onMinuteSelected: (Int) -> Unit, onSecondSelected: (Int) -> Unit) {
    val minuteValues = (0..59).toList()
    val secondValues = (0..59).toList()

    var selectedMinute by remember { mutableStateOf(minuteValues.first()) }
    var selectedSecond by remember { mutableStateOf(secondValues.first()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Padding16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextLarge(text = stringResource(SharedRes.strings.minutes))
                SpinnerWheel(
                    items = minuteValues,
                    selectedItem = selectedMinute,
                    onItemSelected = {
                        selectedMinute = it
                        onMinuteSelected(selectedMinute)
                    }
                )
            }
            Spacer(modifier = Modifier.width(Dimens.Padding16))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextLarge(text = stringResource(SharedRes.strings.seconds))
                SpinnerWheel(
                    items = secondValues,
                    selectedItem = selectedSecond,
                    onItemSelected = {
                        selectedSecond = it
                        onSecondSelected.invoke(it)
                    }
                )
            }
        }
    }
}
