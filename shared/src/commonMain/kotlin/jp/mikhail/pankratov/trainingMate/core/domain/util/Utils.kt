package jp.mikhail.pankratov.trainingMate.core.domain.util

import androidx.compose.ui.graphics.Color
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import kotlinx.datetime.Clock
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

    fun setDifficultyColor(difficulty: SetDifficulty): Color {
        return when (difficulty) {
            SetDifficulty.Light -> Color(0xFFE8F5E9)
            SetDifficulty.Medium -> Color(0xFFFFF9C4)
            SetDifficulty.Hard -> Color(0xFFFFEBEE)
        }
    }

    fun calculateRestSec(lastSetTime: Long, thisSetTime: Long): Long {
        return (thisSetTime - lastSetTime) / 1000
    }

    fun calculateRestTime(lastSetTime: Long, thisSetTime: Long): String {
        val totalSeconds = (thisSetTime - lastSetTime) / 1000
        return formatTimeText(totalSeconds)
    }

    fun formatTimeText(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val minToDisplay = if (minutes < 10) "0$minutes" else minutes.toString()
        val seconds = totalSeconds % 60
        val secondsToDisplay = if (seconds < 10) "0$seconds" else seconds.toString()
        return "Interval $minToDisplay:$secondsToDisplay"
    }
}