package com.uhc.gymtrack.data.exercise

import com.uhc.gymtrack.domain.exercise.Exercise
import database.ExerciseEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ExerciseEntity.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        weight = weight,
        colorHex = colorHex,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}