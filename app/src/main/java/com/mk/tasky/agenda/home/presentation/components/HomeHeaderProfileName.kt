package com.mk.tasky.agenda.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Light
import com.mk.tasky.ui.theme.LightBlue

@Composable
fun HomeHeaderProfileName(
    name: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Light, shape = CircleShape)
            .clickable {
                onProfileClick()
            }
            .padding(8.dp)
    ) {
        Text(text = name, color = LightBlue, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Preview
@Composable
fun HomeHeaderProfileNamePreview() {
    HomeHeaderProfileName(name = "MK", {})
}
