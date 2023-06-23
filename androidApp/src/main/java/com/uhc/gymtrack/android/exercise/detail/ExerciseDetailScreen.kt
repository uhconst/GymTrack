package com.uhc.gymtrack.android.exercise.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uhc.gymtrack.android.uicomponents.ExposedDropdownMenu

@Composable
fun ExerciseDetailScreen(
    exerciseId: Long,
    navController: NavController,
    viewModel: ExerciseDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasExerciseBeenSaved by viewModel.hasExerciseBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasExerciseBeenSaved) {
        if (hasExerciseBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = { FloatingAddButton(viewModel::saveExercise) }
    ) { padding ->
        AddExercise(
            exerciseColor = state.exerciseColor,
            paddingValues = padding,
            exerciseName = state.exerciseName,
            isExerciseNameVisible = state.isExerciseNameVisible,
            onExerciseChanged = viewModel::onExerciseChanged,
            exerciseWeight = state.exerciseWeight,
            isExerciseWeightHintVisible = state.isExerciseWeightHintVisible,
            onExerciseNameFocusChanged = {
                viewModel.onExerciseNameFocusChanged(it.isFocused)
            },
            onExerciseContentChanged = viewModel::onExerciseContentChanged,
            onExerciseContentFocusChanged = {
                viewModel.onExerciseContentFocusChanged(it.isFocused)
            }

        )
    }
}

@Composable
fun FloatingAddButton(
    saveExerciseOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = saveExerciseOnClick,
        backgroundColor = Color.Black
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Save exercise",
            tint = Color.White
        )
    }
}

@Composable
fun AddExercise(
    exerciseColor: Long,
    paddingValues: PaddingValues,
    exerciseName: String,
    isExerciseNameVisible: Boolean,
    onExerciseChanged: (String) -> Unit,
    exerciseWeight: String,
    isExerciseWeightHintVisible: Boolean,
    onExerciseNameFocusChanged: (FocusState) -> Unit,
    onExerciseContentChanged: (String) -> Unit,
    onExerciseContentFocusChanged: (FocusState) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color(exerciseColor))
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        TransparentHintTextField(
            text = exerciseName,
            hint = "Enter a name for the exercise...",
            isHintVisible = isExerciseNameVisible,
            onValueChanged = onExerciseChanged,
            onFocusChanged = onExerciseNameFocusChanged,
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenu(items = listOf("Chest", "Biceps"), onItemSelected = {})
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = exerciseWeight,
            hint = "Enter exercise weight...",
            isHintVisible = isExerciseWeightHintVisible,
            onValueChanged = onExerciseContentChanged,
            onFocusChanged = onExerciseContentFocusChanged,
            singleLine = false,
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun ScreenPreview() {
    Scaffold(
        floatingActionButton = { FloatingAddButton {} }
    ) {
        AddExercise(
            exerciseColor = 0xFFFFFF,
            paddingValues = it,
            exerciseName = "Test",
            isExerciseNameVisible = false,
            onExerciseChanged = {},
            exerciseWeight = "Test 2",
            isExerciseWeightHintVisible = false,
            onExerciseNameFocusChanged = {},
            onExerciseContentChanged = {},
            onExerciseContentFocusChanged = {}

        )
    }
}
