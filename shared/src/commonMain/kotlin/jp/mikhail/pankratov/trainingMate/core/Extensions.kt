package jp.mikhail.pankratov.trainingMate.core

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber

fun List<String>.listToString(): String {
    return this.filterNot { it.isEmpty() }
        .joinToString(separator = ", ")
}

fun String.stringToList(): List<String> {
    return this.split(", ").map { it.trim() }.filterNot { it.isEmpty() }
}

@Composable
fun dev.icerock.moko.resources.StringResource.getString(): String {
    return stringResource(this)
}

fun LocalDate.getIsoWeekNumber(): Long {
    val dayOfYear = this.dayOfYear
    val dayOfWeek = this.dayOfWeek.isoDayNumber
    val weekStartOffset = (dayOfWeek - dayOfYear % 7 + 7) % 7
    val weekNumber: Long = (dayOfYear + weekStartOffset - 1L) / 7
    return if (weekStartOffset >= 4) weekNumber + 1L else weekNumber
}


