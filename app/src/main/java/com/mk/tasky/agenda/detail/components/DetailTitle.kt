package com.mk.tasky.agenda.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.R
import com.mk.tasky.ui.theme.Black

@Composable
fun DetailTitle(
    title: String,
    isEditable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().then(
            if (isEditable) {
                Modifier.clickable {
                    onClick()
                }
            } else Modifier
        ).padding(top = 20.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(9f)) {
            Icon(
                imageVector = Icons.Outlined.Circle,
                contentDescription = "title"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = Black
            )
        }
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
fun DetailTitlePreview() {
    DetailTitle(title = "Project X", true, {})
}
