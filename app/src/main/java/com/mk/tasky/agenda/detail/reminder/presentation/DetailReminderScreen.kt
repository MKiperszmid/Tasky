package com.mk.tasky.agenda.detail.reminder.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.R
import com.mk.tasky.agenda.detail.components.*
import com.mk.tasky.agenda.detail.components.model.ReminderTypes
import com.mk.tasky.core.presentation.TaskyBackground
import com.mk.tasky.ui.theme.Gray
import com.mk.tasky.ui.theme.Light

@Composable
fun DetailReminderScreen(
    reminderTitle: String,
    reminderDescription: String,
    onClose: () -> Unit,
    openEditor: (id: String, title: String, body: String, size: Int) -> Unit,
    viewModel: DetailReminderViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val information = state.editableInformation

    LaunchedEffect(reminderTitle, reminderDescription) {
        viewModel.onEvent(
            DetailReminderEvent.OnUpdatedInformation(
                reminderTitle,
                reminderDescription
            )
        )
    }

    TaskyBackground(header = {
        DetailHeader(
            editingText = stringResource(R.string.edit_reminder),
            date = information.date,
            onClose = onClose,
            onCancelEdit = { viewModel.onEvent(DetailReminderEvent.OnCancelEdit) },
            onEdit = { viewModel.onEvent(DetailReminderEvent.OnEdit) },
            onSave = { viewModel.onEvent(DetailReminderEvent.OnSave) },
            isEditing = state.isEditing
        )
    }) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                DetailColor(
                    text = stringResource(id = R.string.reminder),
                    color = Gray,
                    modifier = Modifier.padding(top = 14.dp)
                )
                DetailTitle(title = information.title, isEditable = state.isEditing, onClick = {
                    openEditor("reminder_title", "Edit Title", information.title, 26)
                })
                Divider(color = Light)
                DetailDescription(
                    description = information.description,
                    isEditable = state.isEditing,
                    onClick = {
                        openEditor(
                            "reminder_description",
                            "Edit Description",
                            information.description,
                            16
                        )
                    }
                )
                Divider(color = Light)
                DetailTimeSelector(
                    text = "At",
                    date = information.date,
                    isEditable = state.isEditing,
                    onTimeClick = {
                        println("Clicked time")
                    },
                    onDateClick = {
                        println("Clicked date")
                    }
                )
                Divider(color = Light)
                DetailNotificationReminder(
                    reminderTypes = ReminderTypes.values().toList(),
                    onClick = {
                        viewModel.onEvent(DetailReminderEvent.OnNotificationReminderClick)
                    },
                    selectedValue = information.reminder,
                    onItemSelected = {
                        viewModel.onEvent(DetailReminderEvent.OnNotificationReminderSelect(it))
                    },
                    showDropdown = state.showDropdown,
                    onDismiss = { viewModel.onEvent(DetailReminderEvent.OnNotificationReminderDismiss) },
                    isEditable = state.isEditing
                )
            }
            Box {
                Divider(color = Light)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DELETE REMINDER",
                        color = Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(DetailReminderEvent.OnReminderDelete)
                        }
                    )
                }
            }
        }
    }
}
