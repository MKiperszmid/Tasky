package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.DarkGray
import com.mk.tasky.ui.theme.Light2
import com.mk.tasky.ui.theme.White

@Composable
fun DetailVisitorsPill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) Black else Light2
    val textColor = if (selected) White else DarkGray
    Box(
        modifier = modifier.clip(RoundedCornerShape(20.dp)).clickable {
            onClick()
        }.background(backgroundColor)
            .padding(vertical = 8.dp)
            .width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor, fontSize = 14.sp)
    }
}

@Preview
@Composable
fun DetailVisitorsPillPreview() {
    DetailVisitorsPill(
        "All",
        false,
        {}
    )
}
