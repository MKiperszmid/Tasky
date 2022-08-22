package com.mk.tasky.agenda.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.R
import com.mk.tasky.ui.theme.Black

@Composable
fun DetailDescription(
    description: String,
    isEditable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable {
            if (isEditable) {
                onClick()
            }
        }.padding(top = 20.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = description, color = Black, fontSize = 16.sp, modifier = Modifier.weight(9f))
        if (isEditable) {
            Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = stringResource(R.string.edit_title),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun DetailDescriptionPreview() {
    DetailDescription(
        description = "Description",
        isEditable = true,
        onClick = {}
    )
}
