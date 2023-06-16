package com.uhc.gymtrack.domain.exercise

import com.uhc.gymtrack.domain.time.DateTimeUtil

class SearchExercises {

    fun execute(exercises: List<Exercise>, query: String): List<Exercise> {
        if(query.isBlank()) {
            return exercises
        }
        return exercises.filter {
            it.name.trim().lowercase().contains(query.lowercase()) ||
                    it.weight.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}