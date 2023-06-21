package com.uhc.gymtrack.di

import com.uhc.gymtrack.data.local.DatabaseDriverFactory
import com.uhc.gymtrack.data.exercise.SqlDelightExerciseDataSource
import com.uhc.gymtrack.data.muscle.SqlDelightMuscleDataSource
import com.uhc.gymtrack.database.ExerciseDatabase
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.uhc.gymtrack.domain.muscle.MuscleDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val exerciseDataSource: ExerciseDataSource by lazy {
        SqlDelightExerciseDataSource(ExerciseDatabase(factory.createDriver()))
    }
    val muscleDataSource: MuscleDataSource by lazy {
        SqlDelightMuscleDataSource(ExerciseDatabase(factory.createDriver()))
    }
}