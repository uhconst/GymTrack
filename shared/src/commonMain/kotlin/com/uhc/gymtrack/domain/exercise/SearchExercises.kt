package com.uhc.gymtrack.domain.exercise

import com.uhc.gymtrack.domain.time.DateTimeUtil

class SearchExercises {

    fun execute(exercises: List<Exercise>, query: String, muscleIds: List<Long>? = null): List<Exercise> {
        var filteredExercise = exercises

        if (query.isNotBlank()) {
            filteredExercise = filterByQuery(exercises, query)
        }

        if (muscleIds?.isNotEmpty() == true) {
            filteredExercise = filterByMuscleId(filteredExercise, muscleIds)
        }

        return filteredExercise
    }

    private fun filterByQuery(exercises: List<Exercise>, query: String): List<Exercise> =
        exercises.filter {
            it.name.trim().lowercase().contains(query.lowercase()) ||
                    it.muscle?.name?.trim()?.lowercase()?.contains(query.lowercase()) ?: false
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.modified)
        }

    private fun filterByMuscleId(exercises: List<Exercise>, muscleIds: List<Long>): List<Exercise> =
        exercises.filter {
            muscleIds.contains(it.muscle?.id)
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.modified)
        }
}