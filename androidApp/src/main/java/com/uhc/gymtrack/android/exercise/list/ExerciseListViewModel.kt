package com.uhc.gymtrack.android.exercise.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.uhc.gymtrack.domain.exercise.SearchExercises
import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.muscle.MuscleDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val exerciseDataSource: ExerciseDataSource,
    private val muscleDataSource: MuscleDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchExercises = SearchExercises()

    private val exercises = savedStateHandle.getStateFlow("exercises", emptyList<Exercise>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)
    private val musclesList = savedStateHandle.getStateFlow<List<Muscle>>("musclesList", emptyList())
    private val muscleIdsFilter =
        savedStateHandle.getStateFlow("muscleIdsFilter", emptyList<Long>())

    val state = combine(
        exercises,
        searchText,
        isSearchActive,
        musclesList,
        muscleIdsFilter
    ) { exercises, searchText, isSearchActive, musclesList, muscleIdsFilter ->
        ExerciseListState(
            exercises = searchExercises.execute(exercises, searchText, muscleIdsFilter),
            searchText = searchText,
            isSearchActive = isSearchActive,
            musclesList = musclesList,
            muscleIdsFilter = muscleIdsFilter
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseListState())

    fun loadExercises() {
        viewModelScope.launch {
            savedStateHandle["exercises"] = exerciseDataSource.getAllExercises()
        }
    }

    fun loadMuscles() {
        viewModelScope.launch {
            savedStateHandle["musclesList"] = muscleDataSource.getAllMuscles()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun onMuscleFilterSelected(muscleId: Long?, selected: Boolean) {
        muscleId?.let {
            val mutableMuscleIds = muscleIdsFilter.value.toMutableList()

            when (selected) {
                true -> mutableMuscleIds.add(muscleId)
                else -> mutableMuscleIds.remove(muscleId)
            }
            savedStateHandle["muscleIdsFilter"] = mutableMuscleIds
        }
    }

    fun onMuscleFilterCanceled() {
        savedStateHandle["muscleIdsFilter"] = emptyList<Long>()
    }

    fun deleteExerciseById(id: Long) {
        viewModelScope.launch {
            exerciseDataSource.deleteExerciseById(id)
            loadExercises()
        }
    }
}