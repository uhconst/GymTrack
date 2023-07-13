package com.uhc.gymtrack.android.exercise.detail

import com.uhc.gymtrack.domain.muscle.Muscle

data class ExerciseDetailState(
    val exerciseName: String = "",
    val exerciseWeight: Double = 0.0,
    val exerciseMuscleId: Long = 0,
    val muscleColor: Long = 0,
    val musclesList: List<Muscle> = emptyList()
)
