package com.uhc.gymtrack.domain.exercise

import com.uhc.gymtrack.presentation.*
import kotlinx.datetime.LocalDateTime

data class Exercise(
    val id: Long?,
    val name: String,
    val weight: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}