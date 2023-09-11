package jp.mikhail.pankratov.trainingMate.core.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {

    fun createDriver(): SqlDriver
}
