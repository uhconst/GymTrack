package com.uhc.gymtrack.data.local

import com.uhc.gymtrack.database.ExerciseDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(ExerciseDatabase.Schema, "gymtrack.db")
    }
}