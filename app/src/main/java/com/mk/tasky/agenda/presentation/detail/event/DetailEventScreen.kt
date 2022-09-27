package com.mk.tasky.agenda.presentation.detail.event

import android.net.Uri
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
import com.mk.tasky.agenda.domain.model.AgendaPhoto
import com.mk.tasky.agenda.presentation.detail.components.*
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import com.mk.tasky.core.presentation.TaskyBackground
import com.mk.tasky.ui.theme.Gray
import com.mk.tasky.ui.theme.Light
import com.mk.tasky.ui.theme.LightGreen

@Composable
fun DetailEventScreen(
    eventTitle: String,
    eventDescription: String,
    onClose: () -> Unit,
    openEditor: (id: String, title: String, body: String, size: Int) -> Unit,
    openPhotoViewer: (Uri) -> Unit,
    deletablePhotoLocation: Uri?,
    viewModel: DetailEventViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(eventTitle, eventDescription) {
        viewModel.onEvent(
            DetailEventEvents.OnUpdatedInformation(
                eventTitle,
                eventDescription
            )
        )
    }

    LaunchedEffect(deletablePhotoLocation) {
        if (deletablePhotoLocation != null) {
            viewModel.onEvent(DetailEventEvents.DeletePhoto(deletablePhotoLocation))
        }
    }

    LaunchedEffect(key1 = state.shouldExit) {
        if (state.shouldExit) {
            onClose()
        }
    }

    TaskyBackground(header = {
        DetailHeader(
            editingText = stringResource(R.string.edit_event),
            date = state.fromDate,
            onClose = onClose,
            onEdit = { viewModel.onEvent(DetailEventEvents.OnEdit) },
            onSave = { viewModel.onEvent(DetailEventEvents.OnSave) },
            isEditing = state.isEditing
        )
    }) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                DetailColor(
                    text = stringResource(id = R.string.event),
                    color = LightGreen,
                    modifier = Modifier.padding(top = 14.dp)
                )
                DetailTitle(title = state.title, isEditable = state.isEditing, onClick = {
                    openEditor(
                        "event_title",
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
                            "event_description",
                            context.getString(R.string.edit_description),
                            state.description,
                            16
                        )
                    }
                )
                DetailPhotoSelector(photos = state.photos, onPhotoSelected = {
                    viewModel.onEvent(DetailEventEvents.OnAddPhoto(AgendaPhoto.Local(it.toString())))
                }, onPhotoClick = {
                        openPhotoViewer(Uri.parse(it.location))
                    })
                Divider(color = Light)
                DetailTimeSelector(
                    text = stringResource(R.string.from),
                    date = state.fromDate,
                    time = state.fromTime,
                    isEditable = state.isEditing,
                    onTimeSelected = {
                        viewModel.onEvent(DetailEventEvents.OnFromTimeSelected(it))
                    },
                    onDateSelected = {
                        viewModel.onEvent(DetailEventEvents.OnFromDateSelected(it))
                    }
                )
                Divider(color = Light)
                DetailTimeSelector(
                    text = stringResource(R.string.to),
                    date = state.toDate,
                    time = state.toTime,
                    isEditable = state.isEditing,
                    onTimeSelected = {
                        viewModel.onEvent(DetailEventEvents.OnToTimeSelected(it))
                    },
                    onDateSelected = {
                        viewModel.onEvent(DetailEventEvents.OnToDateSelected(it))
                    }
                )
                Divider(color = Light)
                DetailNotificationReminder(
                    notificationTypes = NotificationTypes.values().toList(),
                    onClick = {
                        viewModel.onEvent(DetailEventEvents.OnNotificationReminderClick)
                    },
                    selectedValue = state.reminder,
                    onItemSelected = {
                        viewModel.onEvent(DetailEventEvents.OnNotificationReminderSelect(it))
                    },
                    showDropdown = state.isSelectingNotificationReminder,
                    onDismiss = { viewModel.onEvent(DetailEventEvents.OnNotificationReminderDismiss) },
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
                        text = stringResource(R.string.delete_event),
                        color = Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(DetailEventEvents.OnEventDelete)
                        }
                    )
                }
            }
        }
    }
}
