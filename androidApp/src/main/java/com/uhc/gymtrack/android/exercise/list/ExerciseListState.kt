package com.uhc.gymtrack.android.exercise.list

import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.muscle.Muscle

data class ExerciseListState(
    val exercises: List<Exercise> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false,
    val musclesList: List<Muscle> = emptyList(),
    val muscleIdsFilter: List<Long> = emptyList()
)
