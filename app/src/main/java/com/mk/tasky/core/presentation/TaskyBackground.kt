package com.mk.tasky.core.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.White

@Composable
fun TaskyBackground(
    @StringRes title: Int,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = title),
                    fontWeight = FontWeight.Bold,
                    color = White,
                    fontSize = 28.sp
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(8.5f)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                color = White
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}
