package com.mk.tasky.agenda.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.Green
import com.mk.tasky.ui.theme.Light2

@Composable
fun DetailEditable(
    title: String,
    onBack: () -> Unit,
    onSave: (String) -> Unit,
    value: String,
    onTextChange: (String) -> Unit,
    textSize: TextUnit = 26.sp,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "back")
            }
            Text(
                text = title.uppercase(),
                fontSize = 18.sp,
                color = Black,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(
                onClick = {
                    onSave(value)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Green)
            ) {
                Text(text = "Save")
            }
        }
        Divider(color = Light2, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Spacer(modifier = Modifier.height(30.dp))
        BasicTextField(
            value = value,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            onValueChange = { onTextChange(it) },
            textStyle = TextStyle(
                color = Black,
                fontSize = textSize
            )
        )
    }
}

@Preview
@Composable
fun DetailEditablePreview() {
    DetailEditable(
        title = "Edit Title",
        onBack = {},
        onSave = {},
        value = "Project X",
        onTextChange = {}
    )
}
