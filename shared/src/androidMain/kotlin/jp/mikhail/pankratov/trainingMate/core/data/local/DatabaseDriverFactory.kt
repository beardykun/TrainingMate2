package jp.mikhail.pankratov.trainingMate.core.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TrainingDatabase.Schema, context, "training.db")
    }
}