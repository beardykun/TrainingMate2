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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge

@Composable
fun TimerSpinnerWheels(
    minuteValue: Int,
    secondValue: Int,
    onMinuteSelected: (Int) -> Unit,
    onSecondSelected: (Int) -> Unit
) {
    val minuteValues = (0..59).map { it.toString() }.toMutableList().apply {
        addAll(0, listOf("*", "*"))
        addAll(listOf("*", "*"))
    }.toList()
    val secondValues = (0..59).step(5).map { it.toString() }.toMutableList()
        .apply {
            addAll(0, listOf("*", "*"))
            addAll(listOf("*", "*"))
        }

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
                    selectedItem = minuteValue.toString(),
                    onItemSelected = {
                        onMinuteSelected(it)
                    }
                )
            }
            Spacer(modifier = Modifier.width(Dimens.Padding16))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextLarge(text = stringResource(SharedRes.strings.seconds))
                SpinnerWheel(
                    items = secondValues,
                    selectedItem = secondValue.toString(),
                    onItemSelected = {
                        onSecondSelected.invoke(it)
                    }
                )
            }
        }
    }
}
