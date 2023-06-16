package com.uhc.gymtrack.android.exercise.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.uhc.gymtrack.domain.exercise.SearchExercises
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val exerciseDataSource: ExerciseDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchExercises = SearchExercises()

    private val exercises = savedStateHandle.getStateFlow("exercises", emptyList<Exercise>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(exercises, searchText, isSearchActive) { exercises, searchText, isSearchActive ->
        ExerciseListState(
            exercises = searchExercises.execute(exercises, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseListState())

    fun loadExercises() {
        viewModelScope.launch {
            savedStateHandle["exercises"] = exerciseDataSource.getAllExercises()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if(!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteExerciseById(id: Long) {
        viewModelScope.launch {
            exerciseDataSource.deleteExerciseById(id)
            loadExercises()
        }
    }
}