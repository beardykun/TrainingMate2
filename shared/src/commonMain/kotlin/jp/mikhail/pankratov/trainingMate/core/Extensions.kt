package jp.mikhail.pankratov.trainingMate.core

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseSet
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.SetDifficulty
import jp.mikhail.pankratov.trainingMate.core.domain.util.InputError
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
        "${it.weight};${it.reps};${it.difficulty.name};${it.id};${it.updateTime};${it.restSec};${it.restTimeText}"
    }
}

fun String.stringToSetList(): List<ExerciseSet> {
    return this.split(", ").map { it.trim() }.filterNot { it.isEmpty() }.map { dataString ->
        val components = dataString.split(";")

        val weight = components[0]
        val reps = components[1]
        val difficulty = components[2]
        val dif = when (difficulty) {
            SetDifficulty.Light.name -> SetDifficulty.Light
            SetDifficulty.Medium.name -> SetDifficulty.Medium
            SetDifficulty.Hard.name -> SetDifficulty.Hard
            else -> SetDifficulty.Light
        }
        val id = components.getOrNull(3)?.takeIf { it.isNotEmpty() }
        val updateTime = components.getOrNull(4)?.takeIf { it.isNotEmpty() }
        val restSec = components.getOrNull(5)?.takeIf { it.isNotEmpty() && it != "null" }
        val restTimeText = components.getOrNull(6)?.takeIf { it.isNotEmpty() && it != "null" }

        ExerciseSet(
            weight = weight,
            reps = reps,
            difficulty = dif,
            id = id ?: randomUUID(),
            updateTime = updateTime?.toLong() ?: 0L,
            restSec = restSec?.toLong(),
            restTimeText = restTimeText
        )
    }
}

@Composable
fun StringResource.getString(): String {
    return stringResource(this)
}

fun LocalDate.getIsoWeekNumber(): Long {
    val dayOfYear = this.dayOfYear
    val dayOfWeek = this.dayOfWeek.isoDayNumber
    val weekStartOffset = (dayOfWeek - dayOfYear % 7 + 7) % 7
    val weekNumber: Long = (dayOfYear + weekStartOffset - 1L) / 7
    return if (weekStartOffset >= 4) weekNumber + 1L else weekNumber
}

fun InputError.asResId(): StringResource {
    return when (this) {
        InputError.InputErrorReps.INVALID_FORMAT -> SharedRes.strings.invalid_input_format
        InputError.InputErrorReps.EMPTY_REPS -> SharedRes.strings.reps_should_not_be_empty
        InputError.InputErrorReps.REPS_IS_FLOAT -> SharedRes.strings.not_a_float
        InputError.InputErrorReps.REPS_CANT_BE_0 -> SharedRes.strings.not_a_0
        InputError.InputErrorWeight.INVALID_FORMAT -> SharedRes.strings.invalid_input_format
        InputError.InputErrorWeight.WEIGHT_CANT_BE_0 -> SharedRes.strings.weight_cant_be_0
        InputError.InputErrorWeight.USE_DOT_IN_WEIGHT -> SharedRes.strings.use_dot
        InputError.InputErrorWeight.EMPTY_WEIGHT -> SharedRes.strings.weight_should_not_be_empty
    }
}



