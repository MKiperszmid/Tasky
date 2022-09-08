package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mk.tasky.R
import com.mk.tasky.core.presentation.TaskyHeader
import java.time.LocalDate

@Composable
fun DetailHeader(
    editingText: String,
    date: LocalDate,
    onClose: () -> Unit,
    onEdit: () -> Unit,
    onSave: () -> Unit,
    isEditing: Boolean = false,
    modifier: Modifier = Modifier
) {
    TaskyHeader(
        modifier = modifier
    ) {
        IconButton(onClick = {
            onClose()
        }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close)
            )
        }
        val headerText =
            if (isEditing) editingText.uppercase() else "${date.dayOfMonth} ${date.month.name} ${date.year}"
        Text(
            text = headerText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        if (isEditing) {
            Text(
                text = stringResource(id = R.string.save),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onSave() }
            )
        } else {
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit)
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailHeaderPreview() {
    DetailHeader(
        editingText = "Edit Reminder",
        date = LocalDate.now(),
        onClose = {},
        onEdit = {},
        onSave = {},
        isEditing = true
    )
}
