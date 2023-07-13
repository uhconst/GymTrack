package com.uhc.gymtrack.android.muscle

import com.uhc.gymtrack.domain.colors.MuscleColor

data class MuscleDetailState(
    val muscleName: String = "",
    val muscleDescription: String = "",
    val previousColorHexSelected: Long = 0,
    val colorHexSelected: Long = 0,
    val colorsList: List<MuscleColor> = emptyList()
)