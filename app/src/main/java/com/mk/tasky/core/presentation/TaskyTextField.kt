package com.mk.tasky.core.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk.tasky.ui.theme.*

@Composable
fun TaskyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    showError: Boolean = false,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    trailingIcon: ImageVector? = null,
    trailingIconColor: Color? = null,
    onIconClick: () -> Unit = {},
    isPassword: Boolean = false,
    isValid: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        colors = TextFieldDefaults.textFieldColors(
            errorCursorColor = Red,
            errorIndicatorColor = Red,
            errorLabelColor = Red,
            textColor = DarkGray,
            placeholderColor = Gray,
            leadingIconColor = Gray,
            backgroundColor = Light2,
            focusedIndicatorColor = LightBlue,
            unfocusedIndicatorColor = Color.Transparent,
            trailingIconColor = if (isValid) Green else trailingIconColor ?: Gray,
            cursorColor = DarkGray
        ),
        placeholder = {
            Text(text = placeholder)
        },
        isError = showError,
        maxLines = maxLines,
        singleLine = maxLines == 1,
        trailingIcon = {
            TrailingIcon(
                isPassword = isPassword,
                trailingIcon = trailingIcon,
                onIconClick = onIconClick,
                isValid = isValid,
                onPasswordClick = {
                    isPasswordHidden = !isPasswordHidden
                },
                isPasswordHidden = isPasswordHidden
            )
        },
        shape = RoundedCornerShape(10.dp),
        visualTransformation = if (isPassword && isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun TaskyEmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    showError: Boolean = false,
    isValid: Boolean = false,
    modifier: Modifier = Modifier
) {
    TaskyTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        showError = showError,
        modifier = modifier,
        keyboardType = KeyboardType.Email,
        isValid = isValid
    )
}

@Composable
fun TaskyPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    showError: Boolean = false,
    isValid: Boolean = false,
    modifier: Modifier = Modifier
) {
    TaskyTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        showError = showError,
        modifier = modifier,
        keyboardType = KeyboardType.Password,
        isPassword = true,
        isValid = isValid
    )
}

@Composable
private fun TrailingIcon(
    isPassword: Boolean = false,
    isPasswordHidden: Boolean,
    trailingIcon: ImageVector? = null,
    isValid: Boolean = false,
    onPasswordClick: () -> Unit,
    onIconClick: () -> Unit = {}
) {
    if (trailingIcon != null) {
        IconButton(onClick = onIconClick) {
            Icon(imageVector = trailingIcon, contentDescription = "icon")
        }
    } else if (isPassword) {
        IconButton(onClick = onPasswordClick) {
            val icon =
                if (isPasswordHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility
            Icon(imageVector = icon, contentDescription = "icon")
        }
    } else if (isValid) {
        Icon(imageVector = Icons.Default.Check, contentDescription = "icon")
    }
}

@Preview(showBackground = true)
@Composable
fun TaskyTextFieldPreview() {
    TaskyTextField(
        value = "Value",
        onValueChange = {}
    )
}
