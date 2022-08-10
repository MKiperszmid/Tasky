package com.mk.tasky.core.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.White

@Composable
fun TaskyButton(
    text: String,
    onClick: () -> Unit,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Black,
            contentColor = White
        ),
        shape = RoundedCornerShape(if (text.length == 1) 16.dp else 38.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            fontWeight = fontWeight,
            maxLines = 1,
            fontSize = fontSize
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TaskyButtonPreview() {
    TaskyButton(text = "LOG IN", onClick = {})
}
