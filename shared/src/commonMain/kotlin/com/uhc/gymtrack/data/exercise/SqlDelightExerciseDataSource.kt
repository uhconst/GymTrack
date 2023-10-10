package com.uhc.gymtrack.data.exercise

import com.uhc.gymtrack.database.ExerciseDatabase
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.uhc.gymtrack.domain.time.DateTimeUtil

class SqlDelightExerciseDataSource(db: ExerciseDatabase) : ExerciseDataSource {

    private val queries = db.exerciseQueries

    override suspend fun insertExercise(exercise: Exercise, weightChanged: Boolean) {

        // Todo this will always update the weight,
        //  need to save it as a new entry ONLY when it changes
        /** Insert Exercise */
        queries.insertExercise(
            id = exercise.id,
            name = exercise.name,
            created = DateTimeUtil.toEpochMillis(exercise.created),
            modified = DateTimeUtil.toEpochMillis(exercise.modified),
            muscleId = exercise.muscle?.id ?: 0
        )

        if (weightChanged) {
            val exerciseId = queries.lastInsertRowId().executeAsOne()

            /** Insert Weight */
            exercise.weight.apply {
                queries.insertWeight(
                    id = id,
                    weight = weight,
                    unit = unit,
                    created = DateTimeUtil.toEpochMillis(created),
                    exerciseId = exerciseId
                )
            }

            val weightId = queries.lastInsertRowId().executeAsOne()

            /** Insert Exercise Weight bridge table*/
            queries.insertExerciseWeightBridge(
                exerciseId = exerciseId,
                weightId = weightId
            )
        }
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