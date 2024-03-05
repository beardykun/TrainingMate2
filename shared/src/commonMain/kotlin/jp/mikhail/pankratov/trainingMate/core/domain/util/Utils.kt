package jp.mikhail.pankratov.trainingMate.core.domain.util

import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

object Utils {
    fun formatEpochMillisToDate(epochMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.year}/${localDateTime.monthNumber}/${localDateTime.dayOfMonth}"
    }

    fun countTrainingTime(training: Training): String {
        val durationMillis =
            training.endTime?.minus(training.startTime?.seconds?.inWholeSeconds ?: 0)
        val totalSeconds = durationMillis?.div(1000)
        val hours = totalSeconds?.div(3600)
        val minutes = (totalSeconds?.rem(3600))?.div(60)
        val seconds = totalSeconds?.rem(60)
        return "${hours}h:${minutes}m:${seconds}s"
    }

    fun trainingLengthToMin(training: Training): Double {
        val durationMillis =
            training.endTime?.minus(training.startTime?.seconds?.inWholeSeconds ?: 0)
        val totalSeconds = durationMillis?.div(1000)
        return totalSeconds?.div(60)?.toDouble() ?: 0.0
    }
}