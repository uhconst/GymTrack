package com.uhc.gymtrack.data.exercise

import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.muscle.Muscle
import database.ExerciseEntity
import database.GetAllExercises
import database.GetExerciseById
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun GetExerciseById.toExercise() = Exercise(
    id = id,
    name = name,
    weight = weight,
    created = Instant
        .fromEpochMilliseconds(created)
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    modified = Instant
        .fromEpochMilliseconds(modified)
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    muscle = Muscle(
        id = id_,
        name = name_,
        description = description,
        colorHex = colorHex,
        created = Instant
            .fromEpochMilliseconds(created_)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        modified = Instant
            .fromEpochMilliseconds(modified_)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
    )
)

fun GetAllExercises.toExercise() =
    Exercise(
        id = id,
        name = name,
        weight = weight,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        modified = Instant
            .fromEpochMilliseconds(modified)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        muscle = Muscle(
            id = id_,
            name = name_,
            description = description,
            colorHex = colorHex,
            created = Instant
                .fromEpochMilliseconds(created_)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            modified = Instant
                .fromEpochMilliseconds(modified_)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
        )
    )