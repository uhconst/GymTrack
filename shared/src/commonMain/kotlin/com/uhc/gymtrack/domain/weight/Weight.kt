package com.uhc.gymtrack.domain.weight

import kotlinx.datetime.LocalDateTime

data class Weight(
    val id: Long?,
    val weight: Double,
    val unit: String,
    val created: LocalDateTime
) {
    companion object {
        const val UNIT_KG = "KG"
        const val UNIT_OZ = "OZ"
    }
}