package com.mk.tasky.agenda.presentation.detail.task

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
import com.mk.tasky.ui.theme.Green
import com.mk.tasky.ui.theme.Light
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun DetailTaskScreen(
    taskTitle: String,
    taskDescription: String,
    onClose: () -> Unit,
    openEditor: (id: String, title: String, body: String, size: Int) -> Unit,
    viewModel: DetailTaskViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val datepickerState = rememberMaterialDialogState()
    val timepickerState = rememberMaterialDialogState()

    LaunchedEffect(taskTitle, taskDescription) {
        viewModel.onEvent(
            DetailTaskEvent.OnUpdatedInformation(
                taskTitle,
                taskDescription
            )
        )
    }

    LaunchedEffect(key1 = state.shouldExit) {
        if (state.shouldExit) {
            onClose()
        }
    }

    MaterialDialog(
        dialogState = timepickerState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        timepicker { time ->
            viewModel.onEvent(DetailTaskEvent.OnTimeSelected(time))
        }
    }

    MaterialDialog(
        dialogState = datepickerState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker { date ->
            viewModel.onEvent(DetailTaskEvent.OnDateSelected(date))
        }
    }

    TaskyBackground(header = {
        DetailHeader(
            editingText = stringResource(R.string.edit_task),
            date = state.date,
            onClose = onClose,
            onEdit = { viewModel.onEvent(DetailTaskEvent.OnEdit) },
            onSave = { viewModel.onEvent(DetailTaskEvent.OnSave) },
            isEditing = state.isEditing
        )
    }) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                DetailColor(
                    text = stringResource(id = R.string.task),
                    color = Green,
                    modifier = Modifier.padding(top = 14.dp)
                )
                DetailTitle(title = state.title, isEditable = state.isEditing, onClick = {
                    openEditor(
                        "task_title",
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
                            "task_description",
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
                    onTimeClick = {
                        timepickerState.show()
                    },
                    onDateClick = {
                        datepickerState.show()
                    }
                )
                Divider(color = Light)
                DetailNotificationReminder(
                    notificationTypes = NotificationTypes.values().toList(),
                    onClick = {
                        viewModel.onEvent(DetailTaskEvent.OnNotificationSelectorClick)
                    },
                    selectedValue = state.notificationType,
                    onItemSelected = {
                        viewModel.onEvent(DetailTaskEvent.OnNotificationSelectorSelect(it))
                    },
                    showDropdown = state.showDropdown,
                    onDismiss = { viewModel.onEvent(DetailTaskEvent.OnNotificationSelectorDismiss) },
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
                        text = stringResource(R.string.delete_task),
                        color = Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(DetailTaskEvent.OnTaskDelete)
                        }
                    )
                }
            }
        }
    }
}
