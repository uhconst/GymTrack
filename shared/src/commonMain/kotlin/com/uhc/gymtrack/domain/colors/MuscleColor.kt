package com.uhc.gymtrack.domain.colors

import com.uhc.gymtrack.domain.parcelize.CommonParcelable
import com.uhc.gymtrack.domain.parcelize.CommonParcelize
import com.uhc.gymtrack.presentation.BabyBlueHex
import com.uhc.gymtrack.presentation.LightGreenHex
import com.uhc.gymtrack.presentation.RedOrangeHex
import com.uhc.gymtrack.presentation.RedPinkHex
import com.uhc.gymtrack.presentation.VioletHex

@CommonParcelize
data class MuscleColor(
    val hex: Long,
    val name: String
): CommonParcelable {
    companion object {
        val colors = listOf(
            MuscleColor(BabyBlueHex, "Baby Blue"),
            MuscleColor(LightGreenHex, "Light Green"),
            MuscleColor(RedOrangeHex, "Red Orange"),
            MuscleColor(RedPinkHex, "Red Pink"),
            MuscleColor(VioletHex, "Violet")
        )

        fun generateRandomColor() = colors.random()
    }
}
