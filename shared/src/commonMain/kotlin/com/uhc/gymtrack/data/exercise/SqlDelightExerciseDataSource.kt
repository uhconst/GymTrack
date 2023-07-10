package com.uhc.gymtrack.data.exercise

import com.uhc.gymtrack.database.ExerciseDatabase
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.uhc.gymtrack.domain.time.DateTimeUtil

class SqlDelightExerciseDataSource(db: ExerciseDatabase) : ExerciseDataSource {

    private val queries = db.exerciseQueries

    override suspend fun insertExercise(exercise: Exercise) {
        queries.insertExercise(
            id = exercise.id,
            name = exercise.name,
            weight = exercise.weight,
            created = DateTimeUtil.toEpochMillis(exercise.created),
            modified = DateTimeUtil.toEpochMillis(exercise.modified),
            muscleId = exercise.muscle?.id ?: 0
        )
    }

    override suspend fun getExerciseById(id: Long): Exercise? {
        return queries
            .getExerciseById(id)
            .executeAsOneOrNull()
            ?.toExercise()
    }

    override suspend fun getAllExercises(): List<Exercise> {
        return queries
            .getAllExercises()
            .executeAsList()
            .map { it.toExercise() }
    }

    override suspend fun deleteExerciseById(id: Long) {
        queries.deleteExerciseById(id)
    }
}