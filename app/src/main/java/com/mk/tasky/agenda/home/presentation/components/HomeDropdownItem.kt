package com.mk.tasky.agenda.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeDropdownItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().clickable {
            onClick()
        }.padding(start = 20.dp, top = 20.dp, bottom = 20.dp, end = 104.dp)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

@Preview
@Composable
fun HomeDropdownItemPreview() {
    HomeDropdownItem(text = "Event", {})
}
