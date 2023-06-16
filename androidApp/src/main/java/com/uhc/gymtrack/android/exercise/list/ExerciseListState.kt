package com.uhc.gymtrack.android.exercise.list

import com.uhc.gymtrack.domain.exercise.Exercise

data class ExerciseListState(
    val exercises: List<Exercise> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
