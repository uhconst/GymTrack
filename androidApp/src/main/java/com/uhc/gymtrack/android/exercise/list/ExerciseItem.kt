package com.uhc.gymtrack.android.exercise.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uhc.gymtrack.domain.exercise.Exercise
import com.uhc.gymtrack.domain.muscle.Muscle
import com.uhc.gymtrack.domain.time.DateTimeUtil
import com.uhc.gymtrack.presentation.RedOrangeHex
import kotlinx.datetime.LocalDateTime

@Composable
fun ExerciseItem(
    exercise: Exercise,
    backgroundColor: Color,
    onExerciseClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedDate = remember(exercise.created) {
        DateTimeUtil.formatExerciseDate(exercise.created)
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .clickable { onExerciseClick() }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = exercise.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete exercise",
                modifier = Modifier
                    .clickable(MutableInteractionSource(), null) {
                        onDeleteClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = exercise.weightWithKg,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(40)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
            Text(
                text = exercise.muscle?.name ?: "-",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(40)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Modified: $formattedDate",
            color = Color.LightGray,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 11.sp,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Preview
@Composable
fun ExerciseItemPreview() {
    val created = LocalDateTime(2020, 6, 2, 8, 9, 0)
    val modified = LocalDateTime(2023, 10, 10, 5, 15, 0)

    ExerciseItem(
        exercise = Exercise(
            id = null,
            name = "Side polia",
            weight = "35",
//            colorHex = 4623,
            created = created,
            modified = modified,
            muscle = Muscle(
                id = null,
                name = "Shoulder",
                description = "-",
                created = created,
                modified = modified,
                colorHex = RedOrangeHex
            )
        ),
        backgroundColor = Color.Blue,
        onExerciseClick = {},
        onDeleteClick = {},
        modifier = Modifier
    )
}