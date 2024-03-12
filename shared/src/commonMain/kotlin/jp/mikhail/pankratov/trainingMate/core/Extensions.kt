package jp.mikhail.pankratov.trainingMate.core

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

fun List<String>.listToString(): String {
    return this.filterNot { it.isEmpty() }
        .joinToString(separator = ", ")
}

fun String.stringToList(): List<String> {
    return this.split(", ").map { it.trim() }.filterNot { it.isEmpty() }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun dev.icerock.moko.resources.StringResource.getString(): String {
    return stringResource(this)
}


