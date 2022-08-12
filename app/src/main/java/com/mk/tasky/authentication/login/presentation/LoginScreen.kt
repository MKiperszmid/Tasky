package com.mk.tasky.authentication.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.R
import com.mk.tasky.core.presentation.TaskyButton
import com.mk.tasky.core.presentation.TaskyEmailTextField
import com.mk.tasky.core.presentation.TaskyPasswordTextField
import com.mk.tasky.ui.theme.Gray
import com.mk.tasky.ui.theme.Link

@Composable
fun LoginScreen(
    signupClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        Column {
            TaskyEmailTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnEmailChange(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(id = R.string.email_address),
                showError = state.emailError,
                isValid = state.validEmail
            )
            Spacer(modifier = Modifier.height(16.dp))
            TaskyPasswordTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnPasswordChange(it))
                },
                onPasswordIconClick = {
                    viewModel.onEvent(LoginEvent.ChangePasswordVisibility)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(id = R.string.password),
                isTextHidden = state.isPasswordHidden,
                showError = state.passwordError
            )
            Spacer(modifier = Modifier.height(25.dp))
            TaskyButton(
                text = stringResource(id = R.string.log_in),
                onClick = { viewModel.onEvent(LoginEvent.Submit) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = signupClick) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Gray,
                                fontSize = 14.sp
                            )
                        ) {
                            append("DON'T HAVE AN ACCOUNT?")
                            append(" ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Link,
                                fontSize = 14.sp
                            )
                        ) {
                            append("SIGN UP")
                        }
                    }
                )
            }
        }
    }
}
