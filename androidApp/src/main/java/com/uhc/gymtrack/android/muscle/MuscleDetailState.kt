package com.uhc.gymtrack.android.muscle

data class MuscleDetailState(
    val muscleName: String = "",
    val isMuscleNameVisible: Boolean = false,
    val muscleDescription: String = "",
    val isMuscleDescriptionHintVisible: Boolean = false
)