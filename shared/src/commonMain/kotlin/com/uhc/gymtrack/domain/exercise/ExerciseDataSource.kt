package com.uhc.gymtrack.domain.exercise

interface ExerciseDataSource {
    suspend fun insertExercise(exercise: Exercise)
    suspend fun getExerciseById(id: Long): Exercise?
    suspend fun getAllExercises(): List<Exercise>
    suspend fun deleteExerciseById(id: Long)
}