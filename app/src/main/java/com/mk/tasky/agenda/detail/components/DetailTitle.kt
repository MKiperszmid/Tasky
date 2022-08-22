package com.mk.tasky.agenda.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mk.tasky.R
import com.mk.tasky.ui.theme.Black

@Composable
fun DetailTitle(
    title: String,
    isEditing: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = false, onClick = {}, enabled = false)
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 26.sp, color = Black)
        }
        if (isEditing) {
            Icon(imageVector = Icons.Default.NavigateNext, contentDescription = stringResource(R.string.edit_title))
        }
    }
}

@Preview
@Composable
fun DetailTitlePreview() {
    DetailTitle(title = "Project X", true)
}
