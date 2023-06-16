package com.uhc.gymtrack.data.local

import android.content.Context
import com.uhc.gymtrack.database.ExerciseDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(ExerciseDatabase.Schema, context, "gymtrack.db")
    }
}