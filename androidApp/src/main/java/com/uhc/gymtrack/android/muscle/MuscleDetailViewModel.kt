package com.uhc.gymtrack.android.muscle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.gymtrack.domain.colors.MuscleColor
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.muscle.MuscleDataSource
import com.uhc.gymtrack.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MuscleDetailViewModel @Inject constructor(
    private val muscleDataSource: MuscleDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val muscleName = savedStateHandle.getStateFlow("muscleName", "")
    private val isMuscleNameFocused = savedStateHandle.getStateFlow("isMuscleNameFocused", false)
    private val muscleDescription = savedStateHandle.getStateFlow("muscleDescription", "")
    private val isMuscleDescriptionFocused =
        savedStateHandle.getStateFlow("isMuscleDescriptionFocused", false)
    private val muscleModified =
        savedStateHandle.getStateFlow("muscleModified", "") //todo
    private val muscleColor = savedStateHandle.getStateFlow(
        "muscleColor",
        Exercise.generateRandomColor()
    )

    private val colorsList =
        savedStateHandle.getStateFlow<List<MuscleColor>>("colorsList", emptyList())

    val state = combine(
        muscleName,
        isMuscleNameFocused,
        muscleDescription,
        isMuscleDescriptionFocused
    ) { name, isTitleFocused, description, isMuscleDescriptionFocused ->
        MuscleDetailState(
            muscleName = name,
            isMuscleNameVisible = name.isEmpty() && !isTitleFocused,
            muscleDescription = description,
            isMuscleDescriptionHintVisible = description.isEmpty() && !isMuscleDescriptionFocused
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MuscleDetailState())

    val colorState = combine(
        muscleColor,
        colorsList
    ) { muscleColor, colorsList ->
        MuscleColorState(
            colorHexSelected = muscleColor,
            colorsList = colorsList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MuscleColorState())

    private val _hasMuscleBeenSaved = MutableStateFlow(false)
    val hasMuscleBeenSaved = _hasMuscleBeenSaved.asStateFlow()

    private var existingMuscleId: Long? = null
//    private var existingCreatedDate: Long? = null

    init {
        viewModelScope.launch { savedStateHandle["colorsList"] = MuscleColor.colors }

        savedStateHandle.get<Long>("muscleId")?.let { existingMuscleId ->
            if (existingMuscleId == -1L) {
                return@let
            }
            this.existingMuscleId = existingMuscleId
            viewModelScope.launch {
                muscleDataSource.getMuscleById(existingMuscleId)?.let { muscle ->
                    savedStateHandle["muscleName"] = muscle.name
                    savedStateHandle["muscleDescription"] = muscle.description
                    savedStateHandle["muscleModified"] = muscle.modified
                    savedStateHandle["muscleColor"] = muscle.colorHex
                }
            }
        }
    }

    fun onMuscleChanged(text: String) {
        savedStateHandle["muscleName"] = text
    }

    fun onMuscleDescriptionChanged(text: String) {
        savedStateHandle["muscleDescription"] = text
    }

    fun onMuscleNameFocusChanged(isFocused: Boolean) {
        savedStateHandle["isMuscleNameFocused"] = isFocused
    }

    fun onMuscleDescriptionFocusChanged(isFocused: Boolean) {
        savedStateHandle["isMuscleDescriptionFocused"] = isFocused
    }

    fun onColorSelectedChanged(colorHex: Long) {
        savedStateHandle["muscleColor"] = colorHex
    }

    fun saveMuscle() {
        viewModelScope.launch {
            muscleDataSource.insertMuscle(
                Muscle(
                    id = existingMuscleId,
                    name = muscleName.value,
                    description = muscleDescription.value,
                    colorHex = muscleColor.value,
                    created = DateTimeUtil.now() /*if (existingMuscleId == null) DateTimeUtil.now() else*/,
                    modified = DateTimeUtil.now()
                )
            )
            _hasMuscleBeenSaved.value = true
        }
    }
}