package com.uhc.gymtrack.android.note_list

import com.uhc.gymtrack.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
