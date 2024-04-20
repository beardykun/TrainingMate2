package jp.mikhail.pankratov.trainingMate.core.domain.util

import jp.mikhail.pankratov.trainingMate.core.getIsoWeekNumber
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateUtils {
    private val currentDate: LocalDateTime
        get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val currentYear: Long
        get() = currentDate.year.toLong()
    val currentWeekNumber: Long
        get() = currentDate.date.getIsoWeekNumber()
    val currentMonthNumber: Long
        get() = currentDate.monthNumber.toLong()
}