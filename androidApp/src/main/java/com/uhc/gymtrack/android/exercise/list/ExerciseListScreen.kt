package com.uhc.gymtrack.android.exercise.list

import android.R.drawable.ic_menu_save
import android.R.drawable.ic_menu_view
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uhc.gymtrack.android.R.drawable.ic_exercise
import com.uhc.gymtrack.android.R.drawable.ic_muscle
import com.uhc.gymtrack.android.uicomponents.floatingbutton.FabIcon
import com.uhc.gymtrack.android.uicomponents.floatingbutton.MultiFabItem
import com.uhc.gymtrack.android.uicomponents.floatingbutton.MultiFloatingActionButton
import com.uhc.gymtrack.android.uicomponents.floatingbutton.fabOption
import com.uhc.gymtrack.presentation.BabyBlueHex

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ExerciseListScreen(
    navController: NavController, viewModel: ExerciseListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.loadExercises()
        viewModel.loadMuscles()
    }

    Scaffold(floatingActionButton = {
        MultiFloatingActionButton(
            items = listOf(
                MultiFabItem(id = 1, iconRes = ic_exercise, label = "Add Exercise"),
                MultiFabItem(id = 2, iconRes = ic_muscle, label = "Add Muscle"),
                MultiFabItem(id = 3, iconRes = ic_menu_view, label = "Filter")
            ),
            fabIcon = FabIcon(iconRes = ic_menu_save, iconRotate = 45f),
            onFabItemClicked = {
                when (it.id) {
                    1 -> navController.navigate("exercise_detail/-1L")
                    2 -> navController.navigate("muscle_detail/-1L")
                    3 -> openDialog = true
                }
            }, fabOption = fabOption(showLabel = true)
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
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
                    visible = !state.isSearchActive, enter = fadeIn(), exit = fadeOut()
                ) {
                    Text(
                        text = "All exercises", fontWeight = FontWeight.Bold, fontSize = 30.sp
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(items = state.exercises, key = { it.id!! }) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        backgroundColor = Color(exercise.muscle?.colorHex ?: BabyBlueHex),
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

        LazyRow {
            items(items = state.musclesFiltered, key = { it.id!! }) { muscle ->
                FilterItem(text = muscle.name, onClose = {
                    viewModel.onMuscleFilterSelected(muscle.id, false)
                }, onChipClicked = { openDialog = true })
            }
        }
    }

    if (openDialog) {
        AlertDialog(onDismissRequest = { openDialog = false },
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "Filter") },
            title = { Text(text = "Filter") },
            text = {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(count = 3),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalItemSpacing = 8.dp,
                ) {
                    itemsIndexed(state.musclesList) { _, muscle ->
                        var selected by remember {
                            mutableStateOf(viewModel.checkMuscleFilterSelected(muscle.id))
                        }

                        FilterChip(selected = !selected, onClick = {
                            selected = !selected
                            viewModel.onMuscleFilterSelected(muscle.id, selected)
                        }, label = { Text(muscle.name) }, leadingIcon = if (selected) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = muscle.name,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }, modifier = Modifier.wrapContentSize())

                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.onMuscleFilterCanceled()
                    openDialog = false
                }) {
                    Text("Cancel")
                }
            })
    }
}