package jp.mikhail.pankratov.trainingMate.core.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(TrainingDatabase.Schema, "training.db")
    }
}