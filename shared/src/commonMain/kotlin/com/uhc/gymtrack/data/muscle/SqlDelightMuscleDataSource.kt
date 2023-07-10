package com.uhc.gymtrack.data.muscle

import com.uhc.gymtrack.database.ExerciseDatabase
import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.muscle.MuscleDataSource
import com.uhc.gymtrack.domain.time.DateTimeUtil

class SqlDelightMuscleDataSource(db: ExerciseDatabase) : MuscleDataSource {

    private val queries = db.exerciseQueries

    override suspend fun insertMuscle(muscle: Muscle) {
        queries.insertMuscle(
            id = muscle.id,
            name = muscle.name,
            description = muscle.description,
            colorHex = muscle.colorHex,
            created = DateTimeUtil.toEpochMillis(muscle.created),
            modified = DateTimeUtil.toEpochMillis(muscle.modified)
        )
    }

    override suspend fun getMuscleById(id: Long): Muscle? {
        return queries
            .getMuscleById(id)
            .executeAsOneOrNull()
            ?.toMuscle()
    }

    override suspend fun getAllMuscles(): List<Muscle> {
        return queries
            .getAllMuscles()
            .executeAsList()
            .map { it.toMuscle() }
    }

    override suspend fun getAllMusclesNames(): List<String> {
        return queries
            .getAllMuscles()
            .executeAsList()
            .map { it.name }
    }

    override suspend fun deleteMuscleById(id: Long) {
        queries.deleteMuscleById(id)
    }
}