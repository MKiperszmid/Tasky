package com.mk.tasky.agenda.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.agenda.presentation.home.AgendaItem
import com.mk.tasky.agenda.presentation.home.HomeAgendaType
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.DarkGray
import com.mk.tasky.ui.theme.White
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeAgendaItem(
    item: AgendaItem,
    onOptionsClick: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemDate by remember {
        derivedStateOf {
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, HH:mm")
            val date = item.firstDatetime.format(dateFormatter)
            if (item.secondDatetime != null) {
                date + " - ${item.secondDatetime.format(dateFormatter)}"
            } else date
        }
    }
    val textColor = if (item.type == HomeAgendaType.Task) White else Black
    val descriptionColor = if (item.type == HomeAgendaType.Task) White else DarkGray

    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 130.dp)
            .background(color = item.type.color, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.then(
                    if (item.type == HomeAgendaType.Task) {
                        Modifier.clickable {
                            onItemClick()
                        }
                    } else Modifier
                ).weight(9f)
            ) {
                Icon(
                    imageVector = if (item.isDone) Icons.Outlined.CheckCircleOutline else Icons.Outlined.Circle,
                    contentDescription = "title",
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = textColor,
                    textDecoration = if (item.isDone) TextDecoration.LineThrough else null
                )
            }
            IconButton(onClick = onOptionsClick, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "options",
                    tint = textColor
                )
            }
        }
        Text(
            text = item.description,
            fontSize = 14.sp,
            color = descriptionColor,
            modifier = Modifier.padding(start = 36.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = itemDate,
            fontSize = 14.sp,
            color = descriptionColor,
            modifier = Modifier.align(
                Alignment.End
            )
        )
    }
}

@Preview
@Composable
fun HomeReminderPreview() {
    HomeAgendaItem(
        item = AgendaItem(
            id = "",
            title = "Lunch break",
            isDone = false,
            description = "It's time to take a lunch break!",
            type = HomeAgendaType.Reminder,
            firstDatetime = LocalDateTime.now()
        ),
        onOptionsClick = {},
        onItemClick = {}
    )
}
