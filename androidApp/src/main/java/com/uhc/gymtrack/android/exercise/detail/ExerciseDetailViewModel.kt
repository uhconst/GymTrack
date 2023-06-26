package com.uhc.gymtrack.android.exercise.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.muscle.MuscleDataSource
import com.uhc.gymtrack.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val exerciseDataSource: ExerciseDataSource,
    private val muscleDataSource: MuscleDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val exerciseName = savedStateHandle.getStateFlow("exerciseName", "")
    private val isExerciseNameFocused =
        savedStateHandle.getStateFlow("isExerciseNameFocused", false)
    private val exerciseWeight = savedStateHandle.getStateFlow("exerciseWeight", "")
    private val isExerciseWeightFocused =
        savedStateHandle.getStateFlow("isExerciseWeightFocused", false)
    private val exerciseColor = savedStateHandle.getStateFlow(
        "exerciseColor",
        Exercise.generateRandomColor()
    )
    private val created = savedStateHandle.getStateFlow(
        "created",
        DateTimeUtil.now()
    )
    private val muscleId = savedStateHandle.getStateFlow("muscleId", 0L)
    private val musclesList = savedStateHandle.getStateFlow<List<Muscle>?>("musclesList", null)

    val state = combine(
        exerciseName,
        isExerciseNameFocused,
        exerciseWeight,
//        isExerciseWeightFocused,
        musclesList,
        muscleId
    ) { title, isTitleFocused, content, /*isExerciseWeightFocused,*/ musclesList, muscleId ->
        ExerciseDetailState(
            exerciseName = title,
            isExerciseNameVisible = title.isEmpty() && !isTitleFocused,
            exerciseWeight = content,
            isExerciseWeightHintVisible = content.isEmpty() /*&& !isExerciseWeightFocused*/,
            exerciseColor = Exercise.generateRandomColor(),
            musclesList = musclesList ?: emptyList(),
            muscleId = muscleId
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseDetailState())

    private val _hasExerciseBeenSaved = MutableStateFlow(false)
    val hasExerciseBeenSaved = _hasExerciseBeenSaved.asStateFlow()

    private var existingExerciseId: Long? = null

    init {
        savedStateHandle.get<Long>("exerciseId")?.let { existingExerciseId ->
            if (existingExerciseId == -1L) {
                return@let
            }
            this.existingExerciseId = existingExerciseId
            viewModelScope.launch {
                exerciseDataSource.getExerciseById(existingExerciseId)?.let { exercise ->
                    savedStateHandle["exerciseName"] = exercise.name
                    savedStateHandle["exerciseWeight"] = exercise.weight
                    savedStateHandle["exerciseColor"] = exercise.colorHex
                    savedStateHandle["created"] = exercise.created
                    savedStateHandle["muscleId"] = exercise.muscleId
                }
            }
        }

        viewModelScope.launch {
            muscleDataSource.getAllMuscles().let { muscles ->
                savedStateHandle["musclesList"] = muscles
            }
        }
    }

    fun onExerciseChanged(text: String) {
        savedStateHandle["exerciseName"] = text
    }

    fun onExerciseContentChanged(text: String) {
        savedStateHandle["exerciseWeight"] = text
    }

    fun onExerciseNameFocusChanged(isFocused: Boolean) {
        savedStateHandle["isExerciseNameFocused"] = isFocused
    }

    fun onExerciseContentFocusChanged(isFocused: Boolean) {
        savedStateHandle["isExerciseWeightFocused"] = isFocused
    }

    fun onExerciseMuscleChanged(muscleSelectedId: Long) {
        savedStateHandle["muscleId"] = muscleSelectedId
    }

    fun saveExercise() {
        viewModelScope.launch {
            exerciseDataSource.insertExercise(
                Exercise(
                    id = existingExerciseId,
                    name = exerciseName.value,
                    weight = exerciseWeight.value,
                    colorHex = exerciseColor.value,
                    created = created.value,
                    modified = DateTimeUtil.now(),
                    muscleId = muscleId.value
                )
            )
            _hasExerciseBeenSaved.value = true
        }
    }
}