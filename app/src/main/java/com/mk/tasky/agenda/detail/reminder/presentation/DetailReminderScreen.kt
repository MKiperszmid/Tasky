package com.mk.tasky.agenda.detail.reminder.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.R
import com.mk.tasky.agenda.detail.components.DetailColor
import com.mk.tasky.agenda.detail.components.DetailHeader
import com.mk.tasky.agenda.detail.components.DetailTitle
import com.mk.tasky.core.presentation.TaskyBackground
import com.mk.tasky.ui.theme.Gray
import com.mk.tasky.ui.theme.Light

@Composable
fun DetailReminderScreen(
    onClose: () -> Unit,
    viewModel: DetailReminderViewModel = hiltViewModel()
) {
    val state = viewModel.state
    TaskyBackground(header = {
        DetailHeader(
            editingText = stringResource(R.string.edit_reminder),
            date = state.date,
            onClose = onClose,
            onEdit = { viewModel.onEvent(DetailReminderEvent.OnEdit) },
            onSave = { viewModel.onEvent(DetailReminderEvent.OnSave) },
            isEditing = state.isEditing
        )
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            DetailColor(text = stringResource(id = R.string.reminder), color = Gray, modifier = Modifier.padding(16.dp))
            DetailTitle(title = state.title, state.isEditing)
            Divider(modifier = Modifier.padding(10.dp), color = Light)
        }
    }
}
