package jp.mikhail.pankratov.trainingMate.core.domain.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object Utils {
    fun formatEpochMillisToDate(epochMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.year}/${localDateTime.monthNumber}/${localDateTime.dayOfMonth}"
    }
}