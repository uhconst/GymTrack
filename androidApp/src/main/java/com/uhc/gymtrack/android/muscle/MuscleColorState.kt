package com.uhc.gymtrack.android.muscle

import com.uhc.gymtrack.domain.colors.MuscleColor

data class MuscleColorState(
    val colorHexSelected: Long = 0,
    val colorsList: List<MuscleColor> = emptyList()
)
