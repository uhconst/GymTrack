package com.uhc.gymtrack.domain.exercise

import com.uhc.gymtrack.domain.parcelize.CommonParcelable
import com.uhc.gymtrack.domain.parcelize.CommonParcelize
import com.uhc.gymtrack.domain.parcelize.CommonTypeParceler
import com.uhc.gymtrack.domain.parcelize.LocalDateTimeParceler
import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.weight.Weight
import com.uhc.gymtrack.domain.weight.Weight.Companion.UNIT_KG
import com.uhc.gymtrack.presentation.*
import kotlinx.datetime.LocalDateTime

@CommonParcelize // this annotation will be ignored by iOS
data class Exercise(
    val id: Long?,
    val name: String,
    val weight: Weight,
    @CommonTypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val created: LocalDateTime,
    @CommonTypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val modified: LocalDateTime,
    val muscle: Muscle?
) : CommonParcelable {

    val weightWithKg get() = weight.weight.toString() + UNIT_KG.lowercase()

    //todo delete
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random() //todo move somewhere else
    }
}
