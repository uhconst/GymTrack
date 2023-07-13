package com.uhc.gymtrack.android.uicomponents.floatingbutton

import androidx.annotation.DrawableRes
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uhc.gymtrack.android.R

@ExperimentalAnimationApi
@Composable
fun MultiFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<MultiFabItem>,
    fabState: MutableState<MultiFabState> = rememberMultiFabState(),
    fabIcon: FabIcon,
    fabOption: FabOption = fabOption(),
    onFabItemClicked: (fabItem: MultiFabItem) -> Unit,
    stateChanged: (fabState: MultiFabState) -> Unit = {}
) {
    val rotation by animateFloatAsState(
        if (fabState.value == MultiFabState.Expand) {
            fabIcon.iconRotate ?: 0f
        } else {
            0f
        }
    )

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(items.size) { index ->
                    MiniFabItem(
                        item = items[index],
                        fabOption = fabOption,
                        onFabItemClicked = {
                            onFabItemClicked(it)
                            fabState.value = fabState.value.toggleValue()
                        }
                    )
                }

                item {}
            }
        }

        FloatingActionButtonExpand(
            onClick = {
                fabState.value = fabState.value.toggleValue()
                stateChanged(fabState.value)
            },
            rotation
        )
    }
}

@Composable
fun MiniFabItem(
    item: MultiFabItem,
    fabOption: FabOption,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (fabOption.showLabel) {
            Text(
                text = item.label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = fabOption.iconTint,
                modifier = Modifier
                    .background(fabOption.backgroundTint, shape = RoundedCornerShape(40))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }

        SmallFloatingActionButtonExerciseMuscle(
            onClick = { onFabItemClicked(item) },
            item.iconRes
        )
    }
}

@Preview
@Composable
fun SmallFloatingActionButtonExerciseMuscle(
    onClick: () -> Unit = {},
    @DrawableRes iconId: Int = R.drawable.ic_exercise
) {
    SmallFloatingActionButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Add"
        )
    }
}

@Preview
@Composable
fun FloatingActionButtonExpand(
    onClick: () -> Unit = {},
    rotationDegrees: Float
) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            modifier = Modifier.rotate(rotationDegrees)
        )
    }
}