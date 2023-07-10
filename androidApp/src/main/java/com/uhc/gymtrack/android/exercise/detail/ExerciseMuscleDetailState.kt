package com.uhc.gymtrack.android.exercise.detail

import com.uhc.gymtrack.domain.muscle.Muscle

data class ExerciseMuscleDetailState(
    val muscleColor: Long = 0,
    val musclesList: List<Muscle> = emptyList()
)