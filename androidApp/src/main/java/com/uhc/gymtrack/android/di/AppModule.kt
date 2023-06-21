package com.uhc.gymtrack.android.di

import android.app.Application
import com.uhc.gymtrack.data.local.DatabaseDriverFactory
import com.uhc.gymtrack.data.exercise.SqlDelightExerciseDataSource
import com.uhc.gymtrack.database.ExerciseDatabase
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.squareup.sqldelight.db.SqlDriver
import com.uhc.gymtrack.data.muscle.SqlDelightMuscleDataSource
import com.uhc.gymtrack.domain.muscle.MuscleDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideExerciseDataSource(driver: SqlDriver): ExerciseDataSource {
        return SqlDelightExerciseDataSource(ExerciseDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideMuscleDataSource(driver: SqlDriver): MuscleDataSource {
        return SqlDelightMuscleDataSource(ExerciseDatabase(driver))
    }
}