package com.uhc.gymtrack.android.exercise.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.exercise.ExerciseDataSource
import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.muscle.MuscleDataSource
import com.uhc.gymtrack.domain.time.DateTimeUtil
import com.uhc.gymtrack.domain.weight.Weight
import com.uhc.gymtrack.domain.weight.Weight.Companion.UNIT_KG
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
    private val exerciseWeight = savedStateHandle.getStateFlow("exerciseWeight", 0.00)
    private val originalExerciseWeight =
        savedStateHandle.getStateFlow("originalExerciseWeight", 0.00)
    private val muscleColor = savedStateHandle.getStateFlow(
        "muscleColor",
        Exercise.generateRandomColor()
    )
    private val createdExerciseDate = savedStateHandle.getStateFlow("createdExerciseDate", null)
    private val createdWeightDate = savedStateHandle.getStateFlow("createdWeightDate", null)
    private val weightId = savedStateHandle.getStateFlow("weightId", 0L)
    private val exerciseMuscleId = savedStateHandle.getStateFlow("exerciseMuscleId", 0L)
    private val musclesList = savedStateHandle.getStateFlow<List<Muscle>?>("musclesList", null)

    private val hasWeightChanged: Boolean
        get() = weightId.value == 0L || originalExerciseWeight.value != exerciseWeight.value

    val state = combine(
        exerciseName,
        exerciseWeight,
        exerciseMuscleId,
        muscleColor,
        musclesList
    ) { title, content, exerciseMuscleId, muscleColor, musclesList ->
        ExerciseDetailState(
            exerciseName = title,
            exerciseWeight = content,
            exerciseMuscleId = exerciseMuscleId,
            muscleColor = muscleColor,
            musclesList = musclesList ?: emptyList(),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseDetailState())

    val muscleState = combine(
        muscleColor,
        musclesList
    ) { muscleColor, musclesList ->
        ExerciseMuscleDetailState(
            muscleColor = muscleColor,
            musclesList = musclesList ?: emptyList(),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseMuscleDetailState())

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
                    savedStateHandle["exerciseWeight"] = exercise.weight.weight
                    savedStateHandle["originalExerciseWeight"] = exercise.weight.weight
                    savedStateHandle["muscleColor"] = exercise.muscle?.colorHex
                    /*                    savedStateHandle["createdExerciseDate"] = exercise.created // todo issue converting it back
                                        savedStateHandle["createdWeightDate"] = exercise.weight.created*/
                    savedStateHandle["weightId"] = exercise.weight.id
                    savedStateHandle["exerciseMuscleId"] = exercise.muscle?.id
                }
            }
        }

        viewModelScope.launch {
            savedStateHandle["musclesList"] = muscleDataSource.getAllMuscles()
        }
    }

    fun onExerciseChanged(text: String) {
        savedStateHandle["exerciseName"] = text
    }

    fun onExerciseWeightChanged(text: String) {
        savedStateHandle["exerciseWeight"] = text.toDouble()
    }

    fun onExerciseMuscleChanged(muscleSelectedId: Long) {
        savedStateHandle["exerciseMuscleId"] = muscleSelectedId
    }

    fun saveExercise() {
        val exerciseMuscle = musclesList.value?.firstOrNull { it.id == exerciseMuscleId.value }

        viewModelScope.launch {
            exerciseDataSource.insertExercise(
                Exercise(
                    id = existingExerciseId,
                    name = exerciseName.value,
                    weight = Weight(
                        id = null, // This needs to be null to always create a new entry if $hasWeightChanged, never update
                        weight = exerciseWeight.value,
                        unit = UNIT_KG,
                        created = createdWeightDate.value ?: DateTimeUtil.now()
                    ),
                    created = createdExerciseDate.value ?: DateTimeUtil.now(),
                    modified = DateTimeUtil.now(),
                    muscle = exerciseMuscle
                ),
                hasWeightChanged
            )
            _hasExerciseBeenSaved.value = true
        }
    }
}