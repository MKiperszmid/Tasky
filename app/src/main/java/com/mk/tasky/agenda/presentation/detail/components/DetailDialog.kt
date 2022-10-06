package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mk.tasky.core.presentation.TaskyButton
import com.mk.tasky.core.presentation.TaskyEmailTextField
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.White

@Composable
fun DetailDialog(
    title: String,
    emailAddress: String,
    onValueChange: (String) -> Unit,
    onClose: () -> Unit,
    onAdd: (String) -> Unit,
    placeholder: String,
    showError: Boolean,
    isValid: Boolean,
    isLoading: Boolean,
    submitText: String,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onClose) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(White, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                modifier = Modifier
                    .align(End)
                    .size(32.dp)
                    .clickable {
                        onClose()
                    }
            )
            Text(
                text = title,
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(30.dp))
            TaskyEmailTextField(
                value = emailAddress,
                onValueChange = { onValueChange(it) },
                placeholder = placeholder,
                showError = showError,
                isValid = isValid,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            TaskyButton(
                text = submitText,
                onClick = { onAdd(emailAddress) },
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading
            )
        }
    }
}

@Preview
@Composable
fun DetailDialogPreview() {
    DetailDialog(
        "Add Visitor",
        "asd@asd.com",
        {},
        {},
        {},
        "",
        false,
        true,
        false,
        "Add"
    )
}
