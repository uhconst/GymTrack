package com.uhc.gymtrack.android.exercise.detail

import com.uhc.gymtrack.domain.muscle.Muscle

data class ExerciseDetailState(
    val exerciseName: String = "",
    val isExerciseNameVisible: Boolean = false,
    val exerciseWeight: String = "",
    val isExerciseWeightHintVisible: Boolean = false,
    val exerciseColor: Long = 0xFFFFFF,
    val musclesList: List<Muscle> = emptyList(),
    val exerciseMuscleId: Long = 0,
)
