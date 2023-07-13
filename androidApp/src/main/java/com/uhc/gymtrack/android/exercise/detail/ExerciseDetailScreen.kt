package com.uhc.gymtrack.android.exercise.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.DropdownMenuItem
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.ExposedDropdownMenu
import com.uhc.gymtrack.presentation.RedOrangeHex

@Composable
fun ExerciseDetailScreen(
    exerciseId: Long,
    navController: NavController,
    viewModel: ExerciseDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val muscleState by viewModel.muscleState.collectAsState()
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
            exerciseWeight = state.exerciseWeight.toString(),
            isExerciseWeightHintVisible = state.isExerciseWeightHintVisible,
            onExerciseNameFocusChanged = {
                viewModel.onExerciseNameFocusChanged(it.isFocused)
            },
            onExerciseContentChanged = viewModel::onExerciseContentChanged,
            onExerciseContentFocusChanged = {
                viewModel.onExerciseContentFocusChanged(it.isFocused)
            },
            musclesList = muscleState.musclesList.map {
                DropdownMenuItem(
                    id = it.id ?: 0,
                    label = it.name
                )
            }, //todo
            muscleSelectedId = state.exerciseMuscleId,
            onMuscleSelectedChanged = viewModel::onExerciseMuscleChanged,
            muscleColor = muscleState.muscleColor
        )
    }
}

@Composable
fun FloatingAddButton(
    saveExerciseOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = saveExerciseOnClick,
        modifier = Modifier.background(Color.Black)
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
    muscleColor: Long
) {
    Column(
        modifier = Modifier
            .background(Color(muscleColor))
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
            onItemSelected = onMuscleSelectedChanged,
            label = "Muscle"
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .width(100.dp)
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
            onMuscleSelectedChanged = {},
            muscleColor = RedOrangeHex,
        )
    }
}

// TODO InputChip, future implementation
/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterItem(
    text: String,
    onClose: () -> Unit,
) {
    InputChip(
        selected = false,
        modifier = Modifier
            .padding(all = 8.dp)
            .clickable { onClose() },
        label = { Text(text) },
        trailingIcon = {
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        },
        onClick = {},
    )
}

// TODO InputChip, future implementation
@Preview
@Composable
fun PreviewFilterItem() {
    FilterItem(text = "Back", onClose = {})
}*/