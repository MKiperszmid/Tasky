package com.mk.tasky.agenda.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.DarkGray
import com.mk.tasky.ui.theme.Gray

@Composable
fun DetailColor(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(20.dp).background(color, shape = RoundedCornerShape(1.dp)))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, fontWeight = FontWeight.SemiBold, color = DarkGray, fontSize = 16.sp)
    }
}

@Preview
@Composable
fun DetailColorPreview() {
    DetailColor(text = "Reminder", color = Gray)
}
