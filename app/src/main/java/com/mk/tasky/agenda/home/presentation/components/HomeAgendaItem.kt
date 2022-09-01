package com.mk.tasky.agenda.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.DarkGray
import com.mk.tasky.ui.theme.Gray
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeAgendaItem(
    title: String,
    description: String,
    color: Color,
    onOptionsClick: () -> Unit,
    startDatetime: LocalDateTime,
    finishDateTime: LocalDateTime? = null,
    modifier: Modifier = Modifier
) {
    val itemDate by remember {
        derivedStateOf {
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, HH:mm")
            val date = startDatetime.format(dateFormatter)
            if (finishDateTime != null) {
                date + " - ${finishDateTime.format(dateFormatter)}"
            } else date
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 130.dp)
            .background(color = color, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(9f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = "title"
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Black
                )
            }
            IconButton(onClick = onOptionsClick, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = "options")
            }
        }
        Text(text = description, fontSize = 14.sp, color = DarkGray, modifier = Modifier.padding(start = 36.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = itemDate,
            fontSize = 14.sp,
            color = DarkGray,
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
        title = "Lunch break",
        description = "asfasfasf ",
        color = Gray,
        onOptionsClick = {},
        startDatetime = LocalDateTime.now()
    )
}
