package jp.mikhail.pankratov.trainingMate.core.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TrainingDatabase.Schema, context, "training.db")
    }
}