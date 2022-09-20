package com.mk.tasky.agenda.presentation.detail.reminder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.R
import com.mk.tasky.agenda.presentation.detail.components.*
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import com.mk.tasky.core.presentation.TaskyBackground
import com.mk.tasky.ui.theme.Gray
import com.mk.tasky.ui.theme.Light
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun DetailReminderScreen(
    reminderTitle: String,
    reminderDescription: String,
    onClose: () -> Unit,
    openEditor: (id: String, title: String, body: String, size: Int) -> Unit,
    viewModel: DetailReminderViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(reminderTitle, reminderDescription) {
        viewModel.onEvent(
            DetailReminderEvent.OnUpdatedInformation(
                reminderTitle,
                reminderDescription
            )
        )
    }

    LaunchedEffect(key1 = state.shouldExit) {
        if (state.shouldExit) {
            onClose()
        }
    }

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
                    openEditor(
                        "reminder_title",
                        context.getString(R.string.edit_title),
                        state.title,
                        26
                    )
                })
                Divider(color = Light)
                DetailDescription(
                    description = state.description,
                    isEditable = state.isEditing,
                    onClick = {
                        openEditor(
                            "reminder_description",
                            context.getString(R.string.edit_description),
                            state.description,
                            16
                        )
                    }
                )
                Divider(color = Light)
                DetailTimeSelector(
                    text = stringResource(R.string.at),
                    date = state.date,
                    time = state.time,
                    isEditable = state.isEditing,
                    onTimeSelected = {
                        viewModel.onEvent(DetailReminderEvent.OnTimeSelected(it))
                    },
                    onDateSelected = {
                        viewModel.onEvent(DetailReminderEvent.OnDateSelected(it))
                    }
                )
                Divider(color = Light)
                DetailNotificationReminder(
                    notificationTypes = NotificationTypes.values().toList(),
                    onClick = {
                        viewModel.onEvent(DetailReminderEvent.OnNotificationReminderClick)
                    },
                    selectedValue = state.reminder,
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
                        text = stringResource(R.string.delete_reminder),
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
