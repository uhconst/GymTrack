package com.uhc.gymtrack.domain.weight

import com.uhc.gymtrack.domain.parcelize.CommonParcelable
import com.uhc.gymtrack.domain.parcelize.CommonParcelize
import com.uhc.gymtrack.domain.parcelize.CommonTypeParceler
import com.uhc.gymtrack.domain.parcelize.LocalDateTimeParceler
import kotlinx.datetime.LocalDateTime

@CommonParcelize
data class Weight(
    val id: Long?,
    val weight: Double,
    val unit: String,
    @CommonTypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val created: LocalDateTime
) : CommonParcelable {
    companion object {
        const val UNIT_KG = "KG"
        const val UNIT_OZ = "OZ"
    }
}