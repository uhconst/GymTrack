package com.uhc.gymtrack.android.exercise.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.DropdownMenuItem
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.ExposedDropdownMenu

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
            },
            musclesList = state.musclesList.map {
                DropdownMenuItem(
                    id = it.id ?: 0,
                    label = it.name
                )
            }, //todo
            muscleSelectedId = state.exerciseMuscleId,
            onMuscleSelectedChanged = viewModel::onExerciseMuscleChanged
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
    paddingValues: PaddingValues,
    exerciseName: String,
    isExerciseNameVisible: Boolean,
    onExerciseChanged: (String) -> Unit,
    exerciseWeight: String,
    isExerciseWeightHintVisible: Boolean,
    onExerciseNameFocusChanged: (FocusState) -> Unit,
    onExerciseContentChanged: (String) -> Unit,
    onExerciseContentFocusChanged: (FocusState) -> Unit,
    musclesList: List<DropdownMenuItem>,
    muscleSelectedId: Long,
    onMuscleSelectedChanged: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp, 35.dp)
    ) {
        Text(
            text = "Add Exercise",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        TransparentHintTextField(
            text = exerciseName,
            hint = "Exercise:",
            isHintVisible = isExerciseNameVisible,
            onValueChanged = onExerciseChanged,
            onFocusChanged = onExerciseNameFocusChanged,
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp, color = Color.White),
            modifier = Modifier
                .width(100.dp)
                .background(color = Color.DarkGray, shape = RoundedCornerShape(5.dp))
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ExposedDropdownMenu(
            items = musclesList,
            selectedId = muscleSelectedId,
            onItemSelected = onMuscleSelectedChanged
        )
        Spacer(modifier = Modifier.height(20.dp))
        TransparentHintTextField(
            text = exerciseWeight,
            hint = "Enter exercise weight...",
            isHintVisible = isExerciseWeightHintVisible,
            onValueChanged = onExerciseContentChanged,
            onFocusChanged = onExerciseContentFocusChanged,
            singleLine = false,
            textStyle = TextStyle(fontSize = 20.sp, color = Color.White),
            modifier = Modifier
                .weight(1f)
                .background(color = Color.DarkGray, shape = RoundedCornerShape(5.dp))
                .padding(15.dp)
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
            paddingValues = it,
            exerciseName = "Exercise:",
            isExerciseNameVisible = false,
            onExerciseChanged = {},
            exerciseWeight = "Test 2",
            isExerciseWeightHintVisible = false,
            onExerciseNameFocusChanged = {},
            onExerciseContentChanged = {},
            onExerciseContentFocusChanged = {},
            musclesList = emptyList(),
            muscleSelectedId = 0,
            onMuscleSelectedChanged = {}
        )
    }
}
