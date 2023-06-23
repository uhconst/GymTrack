package com.uhc.gymtrack.android.exercise.list

import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uhc.gymtrack.android.uicomponents.floatingbutton.FabIcon
import com.uhc.gymtrack.android.uicomponents.floatingbutton.fabOption
import com.uhc.gymtrack.android.uicomponents.floatingbutton.MultiFabItem
import com.uhc.gymtrack.android.uicomponents.floatingbutton.MultiFloatingActionButton

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ExerciseListScreen(
    navController: NavController,
    viewModel: ExerciseListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadExercises()
    }

    Scaffold(
        floatingActionButton = {
            MultiFloatingActionButton(
                items = listOf(
                    MultiFabItem(
                        id = 1,
                        iconRes = R.drawable.ic_input_get,
                        label = "Add Exercise"
                    ),
                    MultiFabItem(
                        id = 2,
                        iconRes = R.drawable.ic_input_delete,
                        label = "Add Muscle"
                    )
                ),
                fabIcon = FabIcon(iconRes = R.drawable.ic_menu_save, iconRotate = 45f),
                onFabItemClicked = {
                    when (it.id) {
                        1 -> navController.navigate("exercise_detail/-1L")
                        2 -> navController.navigate("muscle_detail/-1L")
                    }
                },
                fabOption = fabOption(
                    iconTint = Color.White,
                    showLabel = true
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                HideableSearchTextField(
                    text = state.searchText,
                    isSearchActive = state.isSearchActive,
                    onTextChange = viewModel::onSearchTextChange,
                    onSearchClick = viewModel::onToggleSearch,
                    onCloseClick = viewModel::onToggleSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                )
                this@Column.AnimatedVisibility(
                    visible = !state.isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "All exercises",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = state.exercises,
                    key = { it.id!! }
                ) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        backgroundColor = Color(exercise.colorHex),
                        onExerciseClick = {
                            navController.navigate("exercise_detail/${exercise.id}")
                        },
                        onDeleteClick = {
                            viewModel.deleteExerciseById(exercise.id!!)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .animateItemPlacement()
                    )
                }
            }
        }
    }
}