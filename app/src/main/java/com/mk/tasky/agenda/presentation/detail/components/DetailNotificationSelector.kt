package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.R
import com.mk.tasky.agenda.presentation.detail.components.model.NotificationTypes
import com.mk.tasky.core.presentation.TaskyDropdown
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.DarkGray
import com.mk.tasky.ui.theme.Gray

@Composable
fun DetailNotificationReminder(
    notificationTypes: List<NotificationTypes>,
    onItemSelected: (NotificationTypes) -> Unit,
    selectedValue: NotificationTypes,
    showDropdown: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    isEditable: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.background(Gray, shape = RoundedCornerShape(5.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    tint = DarkGray,
                    contentDescription = "notification",
                    modifier = Modifier.padding(2.dp)
                )
            }
            Spacer(modifier = Modifier.width(13.dp))
            Text(
                text = selectedValue.type.asString(context),
                fontSize = 16.sp,
                color = Black
            )
        }
        if (isEditable) {
            Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = stringResource(R.string.edit_title)
            )
        }
    }
    TaskyDropdown(
        items = notificationTypes.map { it.type.asString(context) },
        onItemSelected = {
            onItemSelected(notificationTypes[it])
        },
        onDismiss = onDismiss,
        modifier = modifier,
        showDropdown = showDropdown
    )
}

@Preview
@Composable
fun DetailNotificationReminderPreview() {
    DetailNotificationReminder(
        notificationTypes = listOf(
            NotificationTypes.ONE_DAY,
            NotificationTypes.ONE_HOUR
        ),
        onClick = {},
        onDismiss = {},
        showDropdown = true,
        onItemSelected = {},
        selectedValue = NotificationTypes.TEN_MINUTES,
        isEditable = false
    )
}
