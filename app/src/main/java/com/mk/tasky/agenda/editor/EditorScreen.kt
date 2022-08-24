package com.mk.tasky.agenda.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.R
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.Green
import com.mk.tasky.ui.theme.Light2

@Composable
fun EditorScreen(
    title: String,
    onBack: () -> Unit,
    onSave: (String) -> Unit,
    textSize: TextUnit = 26.sp,
    viewModel: EditableViewModel = hiltViewModel()
) {
    val body = viewModel.body
    Column(modifier = Modifier.fillMaxSize()) {
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
                    onSave(body)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Green)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
        Divider(color = Light2, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Spacer(modifier = Modifier.height(30.dp))
        BasicTextField(
            value = body,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            onValueChange = { viewModel.onTextChange(it) },
            textStyle = TextStyle(
                color = Black,
                fontSize = textSize
            )
        )
    }
}
