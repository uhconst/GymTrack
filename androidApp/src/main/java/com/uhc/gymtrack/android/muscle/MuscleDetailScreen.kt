package com.uhc.gymtrack.android.muscle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.uhc.gymtrack.android.exercise.detail.TransparentHintTextField
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.DropdownMenuItem
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.ExposedDropdownMenu

@Composable
fun MuscleDetailScreen(
    navController: NavController,
    viewModel: MuscleDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val colorState by viewModel.colorState.collectAsState()
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
            },
            colorsList = colorState.colorsList.map {
                DropdownMenuItem(
                    id = it.hex,
                    label = it.name
                )
            }, //todo
            colorSelectedId = colorState.colorHexSelected,
            onColorSelectedChanged = viewModel::onColorSelectedChanged
        )
    }
}

@Composable
fun FloatingAddButton(
    saveMuscleOnClick: () -> Unit
) {
    FloatingActionButton(
        onClick = saveMuscleOnClick,
        modifier = Modifier.background(Color.Black)
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
    onMuscleDescriptionFocusChanged: (FocusState) -> Unit,
    colorsList: List<DropdownMenuItem>,
    colorSelectedId: Long,
    onColorSelectedChanged: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp, 35.dp)
    ) {
        Text(
            text = "Add Muscle",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        TransparentHintTextField(
            text = muscleName,
            hint = "Muscle:",
            isHintVisible = isMuscleNameVisible,
            onValueChanged = onMuscleChanged,
            onFocusChanged = onMuscleNameFocusChanged,
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp, color = Color.White),
            modifier = Modifier
                .width(100.dp)
                .background(color = Color.DarkGray, shape = RoundedCornerShape(5.dp))
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ExposedDropdownMenu(
            items = colorsList,
            selectedId = colorSelectedId,
            onItemSelected = onColorSelectedChanged
        )
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = muscleDescription,
            hint = "Enter the muscle description...",
            isHintVisible = isMuscleDescriptionHintVisible,
            onValueChanged = onMuscleDescriptionChanged,
            onFocusChanged = onMuscleDescriptionFocusChanged,
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
        AddMuscle(
            paddingValues = it,
            muscleName = "Muscle:",
            isMuscleNameVisible = false,
            onMuscleChanged = {},
            muscleDescription = "Test muscle",
            isMuscleDescriptionHintVisible = false,
            onMuscleNameFocusChanged = {},
            onMuscleDescriptionChanged = {},
            onMuscleDescriptionFocusChanged = {},
            colorsList = emptyList(),
            colorSelectedId = 0,
            onColorSelectedChanged = {}
        )
    }
}

