package com.mk.tasky.agenda.detail.reminder.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                DetailColor(
                    text = stringResource(id = R.string.reminder),
                    color = Gray,
                    modifier = Modifier.padding(top = 14.dp)
                )
                DetailTitle(title = state.title, isEditable = state.isEditing, onClick = {
                    println("Clicked title")
                })
                Divider(color = Light)
                DetailDescription(
                    description = state.description,
                    isEditable = state.isEditing,
                    onClick = {
                        println("Clicked description")
                    }
                )
                Divider(color = Light)
                DetailTimeSelector(
                    text = "From",
                    date = state.date,
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
                    selectedValue = state.selectedReminder,
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
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 48.dp),
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
