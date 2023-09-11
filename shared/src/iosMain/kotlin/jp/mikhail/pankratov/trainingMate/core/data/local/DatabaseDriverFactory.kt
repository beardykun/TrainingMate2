package jp.mikhail.pankratov.trainingMate.core.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(TrainingDatabase.Schema, "training.db")
    }
}