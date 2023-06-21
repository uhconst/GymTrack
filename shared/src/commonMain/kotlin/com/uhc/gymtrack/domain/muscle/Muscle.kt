package com.uhc.gymtrack.domain.muscle

import kotlinx.datetime.LocalDateTime

data class Muscle(
    val id: Long?,
    val name: String,
    val description: String,
    val created: LocalDateTime,
    val modified: LocalDateTime
)
