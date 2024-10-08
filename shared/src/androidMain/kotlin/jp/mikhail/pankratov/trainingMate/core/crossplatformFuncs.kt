package jp.mikhail.pankratov.trainingMate.core

import java.util.UUID

actual fun randomUUID() = UUID.randomUUID().toString()

actual fun formatNumberWithCommas(number: String): String {
    return number.toLong().let { java.text.NumberFormat.getNumberInstance().format(it) }
}
