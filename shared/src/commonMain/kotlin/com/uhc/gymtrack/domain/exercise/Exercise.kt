package com.uhc.gymtrack.domain.exercise

import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.presentation.*
import kotlinx.datetime.LocalDateTime

data class Exercise(
    val id: Long?,
    val name: String,
    val weight: String,
    val colorHex: Long,
    val created: LocalDateTime,
    val modified: LocalDateTime,
    val muscle: Muscle?
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}
