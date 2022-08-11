package com.mk.tasky.authentication.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.R
import com.mk.tasky.core.presentation.TaskyButton
import com.mk.tasky.core.presentation.TaskyEmailTextField
import com.mk.tasky.core.presentation.TaskyPasswordTextField

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        TaskyEmailTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(LoginEvent.OnEmailChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(id = R.string.email_address),
            showError = state.emailError
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
}
