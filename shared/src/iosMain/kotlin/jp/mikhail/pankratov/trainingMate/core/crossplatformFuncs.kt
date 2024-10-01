package jp.mikhail.pankratov.trainingMate.core

import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()

actual fun formatNumberWithCommas(number: String): String {
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatter.defaultFormatterBehavior()
    val nsNumber = formatter.numberFromString(number) ?: return ""
    return formatter.stringFromNumber(nsNumber) ?: ""
}
