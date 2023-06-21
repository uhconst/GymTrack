package com.uhc.gymtrack.data.muscle

import com.uhc.gymtrack.domain.muscle.Muscle
import database.MuscleEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun MuscleEntity.toMuscle(): Muscle {
    return Muscle(
        id = id,
        name = name,
        description = description,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        modified = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}