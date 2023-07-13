package com.uhc.gymtrack.android.exercise.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterItem(
    text: String,
    onClose: () -> Unit,
    onChipClicked: () -> Unit,
) {
    InputChip(
        selected = false,
        modifier = Modifier
            .padding(all = 8.dp)
            .wrapContentSize()
            .clickable { onClose() },
        label = { Text(text) },
        trailingIcon = {
            IconButton(
                onClick = onClose, modifier = Modifier
                    .size(10.dp)
                    .wrapContentSize()
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            disabledSelectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onSecondary
        ),
        onClick = onChipClicked,
    )
}

@Preview
@Composable
fun PreviewFilterItem() {
    val muscleList = listOf("Chest", "Arm", "Triceps", "Shoulder", "A")

    LazyRow {
        items(items = muscleList, key = { it }) { exercise ->
            FilterItem(text = exercise, onClose = {}, onChipClicked = {})
        }
    }
}