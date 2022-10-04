package com.mk.tasky.agenda.presentation.detail.event

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    viewModel: DetailEventViewModel = hiltViewModel(),
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
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (state.showDialog) {
                DetailDialog(
                    title = stringResource(R.string.add_visitor),
                    onClose = { viewModel.onEvent(DetailEventEvents.OnCloseDialog) },
                    onAdd = { viewModel.onEvent(DetailEventEvents.OnAddVisitor(it)) },
                    placeholder = stringResource(R.string.email_address),
                    showError = state.isErrorDialog,
                    isValid = state.isValidDialog,
                    submitText = stringResource(R.string.add),
                    emailAddress = state.dialogEmail,
                    onValueChange = { viewModel.onEvent(DetailEventEvents.OnValueChangeDialog(it)) },
                    isLoading = state.isLoadingDialog
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                DetailColor(
                    text = stringResource(id = R.string.event),
                    color = LightGreen,
                    modifier = Modifier.padding(top = 14.dp)
                )
            }
            item {
                DetailTitle(title = state.title, isEditable = state.isEditing, onClick = {
                    openEditor(
                        "event_title",
                        context.getString(R.string.edit_title),
                        state.title,
                        26
                    )
                })
                Divider(color = Light)
            }
            item {
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
            }
            item {
                DetailPhotoSelector(photos = state.photos, onPhotoSelected = {
                    viewModel.onEvent(DetailEventEvents.OnAddPhoto(AgendaPhoto.Local(it.toString())))
                }, onPhotoClick = {
                    openPhotoViewer(Uri.parse(it.location))
                })
                Divider(color = Light)
            }
            item {
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
            }
            item {
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
            }
            item {
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
                Divider(color = Light)
            }
            item {
                DetailVisitors(
                    visitors = state.attendees,
                    hostId = state.hostId,
                    selectedFilterType = state.selectedFilterType,
                    onFilterTypeClick = {
                        viewModel.onEvent(DetailEventEvents.OnFilterTypeSelect(it))
                    },
                    isEditable = state.isEditing,
                    onAddVisitorClick = {
                        viewModel.onEvent(DetailEventEvents.OnOpenDialog)
                    },
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
            item {
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
}
