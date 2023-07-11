package com.uhc.gymtrack.domain.exercise

import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.weight.Weight
import com.uhc.gymtrack.domain.weight.Weight.Companion.UNIT_KG
import com.uhc.gymtrack.presentation.*
import kotlinx.datetime.LocalDateTime

data class Exercise(
    val id: Long?,
    val name: String,
    val weight: Weight,
    val created: LocalDateTime,
    val modified: LocalDateTime,
    val muscle: Muscle?
) {
    val weightWithKg get() = weight.weight.toString() + UNIT_KG.lowercase()

    //todo delete
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random() //todo move somewhere else
    }
}
