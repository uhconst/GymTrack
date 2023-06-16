package com.uhc.gymtrack.di

import com.uhc.gymtrack.data.local.DatabaseDriverFactory
import com.uhc.gymtrack.data.note.SqlDelightNoteDataSource
import com.uhc.gymtrack.database.NoteDatabase
import com.uhc.gymtrack.domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}