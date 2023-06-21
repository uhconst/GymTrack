package com.uhc.gymtrack.android.muscle

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
import com.uhc.gymtrack.android.exercise.detail.TransparentHintTextField

@Composable
fun MuscleDetailScreen(
    navController: NavController,
    viewModel: MuscleDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasMuscleBeenSaved by viewModel.hasMuscleBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasMuscleBeenSaved) {
        if (hasMuscleBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = { FloatingAddButton(viewModel::saveMuscle) }
    ) { padding ->
        AddMuscle(
            paddingValues = padding,
            muscleName = state.muscleName,
            isMuscleNameVisible = state.isMuscleNameVisible,
            onMuscleChanged = viewModel::onMuscleChanged,
            muscleDescription = state.muscleDescription,
            isMuscleDescriptionHintVisible = state.isMuscleDescriptionHintVisible,
            onMuscleNameFocusChanged = {
                viewModel.onMuscleNameFocusChanged(it.isFocused)
            },
            onMuscleDescriptionChanged = viewModel::onMuscleDescriptionChanged,
            onMuscleDescriptionFocusChanged = {
                viewModel.onMuscleDescriptionFocusChanged(it.isFocused)
            }

        )
    }
}

@Composable
fun FloatingAddButton(
    saveMuscleOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = saveMuscleOnClick,
        backgroundColor = Color.Black
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Save muscle",
            tint = Color.White
        )
    }
}

@Composable
fun AddMuscle(
    paddingValues: PaddingValues,
    muscleName: String,
    isMuscleNameVisible: Boolean,
    onMuscleChanged: (String) -> Unit,
    muscleDescription: String,
    isMuscleDescriptionHintVisible: Boolean,
    onMuscleNameFocusChanged: (FocusState) -> Unit,
    onMuscleDescriptionChanged: (String) -> Unit,
    onMuscleDescriptionFocusChanged: (FocusState) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        TransparentHintTextField(
            text = muscleName,
            hint = "Enter a name for the muscle...",
            isHintVisible = isMuscleNameVisible,
            onValueChanged = onMuscleChanged,
            onFocusChanged = onMuscleNameFocusChanged,
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = muscleDescription,
            hint = "Muscle description...",
            isHintVisible = isMuscleDescriptionHintVisible,
            onValueChanged = onMuscleDescriptionChanged,
            onFocusChanged = onMuscleDescriptionFocusChanged,
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
        AddMuscle(
            paddingValues = it,
            muscleName = "Back",
            isMuscleNameVisible = false,
            onMuscleChanged = {},
            muscleDescription = "Test 2",
            isMuscleDescriptionHintVisible = false,
            onMuscleNameFocusChanged = {},
            onMuscleDescriptionChanged = {},
            onMuscleDescriptionFocusChanged = {}
        )
    }
}

