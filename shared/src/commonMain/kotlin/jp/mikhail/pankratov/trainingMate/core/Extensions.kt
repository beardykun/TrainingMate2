package jp.mikhail.pankratov.trainingMate.core

fun List<String>.listToString(): String {
    return this.filterNot { it.isEmpty() }
        .joinToString(separator = ", ")
}

fun String.stringToList(): List<String> {
    return this.split(", ")
}

