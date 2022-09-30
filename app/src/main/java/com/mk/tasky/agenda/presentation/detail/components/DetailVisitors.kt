package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.R
import com.mk.tasky.agenda.domain.model.Attendee
import com.mk.tasky.agenda.presentation.detail.event.DetailEventFilterType
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.Light2
import com.mk.tasky.ui.theme.LightBlue
import java.time.LocalDateTime

@Composable
fun DetailVisitors(
    visitors: List<Attendee>,
    hostId: String,
    selectedFilterType: DetailEventFilterType,
    onFilterTypeClick: (DetailEventFilterType) -> Unit,
    onAddVisitorClick: () -> Unit,
    isEditable: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Visitors", fontSize = 20.sp, color = Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(16.dp))
            if (isEditable) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Light2)
                        .clickable {
                            onAddVisitorClick()
                        }
                        .size(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "+", fontSize = 20.sp, color = LightBlue)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        DetailVisitorsFilter(
            onClick = { onFilterTypeClick(it) },
            selectedFilterType = selectedFilterType
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (selectedFilterType != DetailEventFilterType.NOT_GOING) {
            val going = remember(visitors) {
                visitors.filter { it.isGoing }
            }

            DetailVisitorsList(
                title = stringResource(R.string.going),
                hostId = hostId,
                isEditable = isEditable,
                attendees = going
            )
        }

        if (selectedFilterType == DetailEventFilterType.ALL) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (selectedFilterType != DetailEventFilterType.GOING) {
            val notGoing = remember(visitors) {
                visitors.filter { !it.isGoing }
            }
            DetailVisitorsList(
                title = stringResource(R.string.not_going),
                hostId = hostId,
                isEditable = isEditable,
                attendees = notGoing
            )
        }
    }
}

@Preview
@Composable
fun DetailVisitorsPreview() {
    DetailVisitors(
        listOf(
            Attendee("asd@asd.com", "Michael Scott", "1", "1", true, LocalDateTime.now()),
            Attendee("asd@asd.com", "Dwyght Schrute", "1", "1", false, LocalDateTime.now())
        ),
        "",
        selectedFilterType = DetailEventFilterType.ALL,
        {},
        {},
        true
    )
}
