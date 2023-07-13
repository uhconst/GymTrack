package com.uhc.gymtrack.android.muscle

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.DropdownMenuItem
import com.uhc.gymtrack.android.uicomponents.dropdownmenu.ExposedDropdownMenu
import com.uhc.gymtrack.presentation.BabyBlueHex
import com.uhc.gymtrack.presentation.RedOrangeHex
import kotlinx.coroutines.launch

@Composable
fun MuscleDetailScreen(
    navController: NavController,
    viewModel: MuscleDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasMuscleBeenSaved by viewModel.hasMuscleBeenSaved.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val animationBackgroundScale = remember { Animatable(initialValue = 0f) }
    val animationBackgroundCorner = remember { Animatable(initialValue = 100f) }

    LaunchedEffect(key1 = hasMuscleBeenSaved) {
        if (hasMuscleBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = { FloatingAddButton(viewModel::saveMuscle) },
    ) { padding ->
        AddMuscle(
            paddingValues = padding,
            muscleName = state.muscleName,
            onMuscleChanged = viewModel::onMuscleChanged,
            muscleDescription = state.muscleDescription,
            onMuscleDescriptionChanged = viewModel::onMuscleDescriptionChanged,
            colorsList = state.colorsList.map {
                DropdownMenuItem(
                    id = it.hex,
                    label = it.name
                )
            }, //todo
            colorSelectedId = state.colorHexSelected,
            onColorSelectedChanged = {
                coroutineScope.launch {
                    animationBackgroundScale.snapTo(0f)
                    animationBackgroundScale.animateTo(
                        targetValue = 1f,
                        animationSpec = SpringSpec(
                            dampingRatio = DampingRatioNoBouncy,
                            stiffness = 100f
                        )
                    )
                }

                coroutineScope.launch {
                    animationBackgroundCorner.snapTo(100f)
                    animationBackgroundCorner.animateTo(
                        targetValue = 0f,
                        animationSpec = SpringSpec(
                            dampingRatio = DampingRatioNoBouncy,
                            stiffness = 20f
                        ),
                    )
                }
                viewModel.onColorSelectedChanged(it)
            },
            animatableScale = animationBackgroundScale,
            roundedCornerShapePercentage = animationBackgroundCorner,
            backgroundColorState = state.previousColorHexSelected
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
    onMuscleChanged: (String) -> Unit,
    muscleDescription: String,
    onMuscleDescriptionChanged: (String) -> Unit,
    colorsList: List<DropdownMenuItem>,
    colorSelectedId: Long,
    onColorSelectedChanged: (Long) -> Unit,
    animatableScale: Animatable<Float, AnimationVector1D>,
    roundedCornerShapePercentage: Animatable<Float, AnimationVector1D>,
    backgroundColorState: Long
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(backgroundColorState))
    )

    Box(
        Modifier
            .scale(scale = animatableScale.value)
            .clip(
                RoundedCornerShape(roundedCornerShapePercentage.value.toInt())
            )
            .fillMaxSize()
            .background(Color(colorSelectedId))
    )

    Column(
        modifier = Modifier
            .background(Color.Transparent)
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
        TextField(
            value = muscleName,
            onValueChange = onMuscleChanged,
            label = { Text("Muscle") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        ExposedDropdownMenu(
            items = colorsList,
            selectedId = colorSelectedId,
            onItemSelected = onColorSelectedChanged
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = muscleDescription,
            onValueChange = onMuscleDescriptionChanged,
            label = { Text("Description") },
            maxLines = 10,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@SuppressLint("UnrememberedAnimatable", "UnrememberedMutableState")
@Preview
@Composable
fun ScreenPreview() {
    Scaffold(
        floatingActionButton = { FloatingAddButton {} }
    ) {
        AddMuscle(
            paddingValues = it,
            muscleName = "Chest",
            onMuscleChanged = {},
            muscleDescription = "Test muscle",
            onMuscleDescriptionChanged = {},
            colorsList = emptyList(),
            colorSelectedId = RedOrangeHex,
            onColorSelectedChanged = {},
            animatableScale = Animatable(initialValue = .4f),
            roundedCornerShapePercentage = Animatable(initialValue = 100f),
            backgroundColorState = BabyBlueHex
        )
    }
}

