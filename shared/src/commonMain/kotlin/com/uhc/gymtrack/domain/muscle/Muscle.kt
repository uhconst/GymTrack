package com.uhc.gymtrack.domain.muscle

import com.uhc.gymtrack.domain.parcelize.CommonParcelable
import com.uhc.gymtrack.domain.parcelize.CommonParcelize
import com.uhc.gymtrack.domain.parcelize.CommonTypeParceler
import com.uhc.gymtrack.domain.parcelize.LocalDateTimeParceler
import kotlinx.datetime.LocalDateTime

@CommonParcelize
data class Muscle(
    val id: Long?,
    val name: String,
    val description: String,
    val colorHex: Long,
    @CommonTypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val created: LocalDateTime,
    @CommonTypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val modified: LocalDateTime
): CommonParcelable