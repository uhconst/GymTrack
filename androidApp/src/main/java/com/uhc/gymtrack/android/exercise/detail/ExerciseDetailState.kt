package com.uhc.gymtrack.android.exercise.detail

data class ExerciseDetailState(
    val exerciseName: String = "",
    val isExerciseNameVisible: Boolean = false,
    val exerciseWeight: String = "",
    val isExerciseWeightHintVisible: Boolean = false,
    val exerciseMuscleId: Long = 0
)
