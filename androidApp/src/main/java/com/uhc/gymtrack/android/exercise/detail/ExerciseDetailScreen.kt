package com.uhc.gymtrack.android.exercise.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            onExerciseChanged = viewModel::onExerciseChanged,
            exerciseWeight = state.exerciseWeight.toString(),
            onExerciseContentChanged = viewModel::onExerciseWeightChanged,
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
    onExerciseChanged: (String) -> Unit,
    exerciseWeight: String,
    onExerciseContentChanged: (String) -> Unit,
    musclesList: List<DropdownMenuItem>,
    muscleSelectedId: Long,
    onMuscleSelectedChanged: (Long) -> Unit,
    muscleColor: Long
) {
    val pattern = remember { Regex("^\\d+\$") } ///todo util

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
        TextField(
            value = exerciseName,
            onValueChange = onExerciseChanged,
            label = { Text("Exercise") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        ExposedDropdownMenu(
            items = musclesList,
            selectedId = muscleSelectedId,
            onItemSelected = onMuscleSelectedChanged,
            label = "Muscle"
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = exerciseWeight,
            onValueChange = onExerciseContentChanged,
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.width(80.dp)
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
            onExerciseChanged = {},
            exerciseWeight = "32",
            onExerciseContentChanged = {},
            musclesList = emptyList(),
            muscleSelectedId = 0,
            onMuscleSelectedChanged = {},
            muscleColor = RedOrangeHex,
        )
    }
}