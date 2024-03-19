package jp.mikhail.pankratov.trainingMate.core

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber

fun List<String>.listToString(): String {
    return this.filterNot { it.isEmpty() }
        .joinToString(separator = ", ")
}

fun String.stringToList(): List<String> {
    return this.split(", ").map { it.trim() }.filterNot { it.isEmpty() }
}

fun List<ExerciseSet>.setListToString(): String {
    return this.filterNot { it.weight.isEmpty() }.joinToString(separator = ", ") {
        "${it.weight};${it.reps};${it.difficulty}"
    }
}

fun String.stringToSetList(): List<ExerciseSet> {
    return this.split(", ").map { it.trim() }.filterNot { it.isEmpty() }.map {
        val (weight, reps, difficulty) = it.split(";")
        ExerciseSet(weight = weight, reps = reps, difficulty = difficulty)
    }
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


