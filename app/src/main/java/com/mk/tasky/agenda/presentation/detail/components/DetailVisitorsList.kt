package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.agenda.domain.model.Attendee
import com.mk.tasky.ui.theme.DarkGray
import java.time.LocalDateTime

@Composable
fun DetailVisitorsList(
    title: String,
    hostId: String,
    isEditable: Boolean,
    attendees: List<Attendee>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = title, fontSize = 16.sp, color = DarkGray, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            attendees.forEach {
                DetailVisitorItem(
                    imageUrl = null, // TODO: See how to get the Attendee's profile pic
                    name = it.fullName,
                    isHost = it.userId == hostId,
                    isEditing = isEditable
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun DetailVisitorsListPreview() {
    DetailVisitorsList(
        "Going!",
        "",
        true,
        listOf(
            Attendee("asd@asd.com", "Michael Scott", "1", "1", true, LocalDateTime.now()),
            Attendee("asd@asd.com", "Dwyght Schrute", "1", "1", true, LocalDateTime.now())
        )
    )
}
