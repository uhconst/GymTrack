package com.uhc.gymtrack.android.uicomponents.floatingbutton

import androidx.annotation.DrawableRes

data class MultiFabItem(
    val id: Int,
    @DrawableRes val iconRes: Int,
    val label: String = "",
)
