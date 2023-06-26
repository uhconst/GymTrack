package com.uhc.gymtrack.domain.muscle

interface MuscleDataSource {
    suspend fun insertMuscle(muscle: Muscle)
    suspend fun getMuscleById(id: Long): Muscle?
    suspend fun getAllMuscles(): List<Muscle>
    suspend fun getAllMusclesNames(): List<String>
    suspend fun deleteMuscleById(id: Long)
}