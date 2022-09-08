package com.mk.tasky.authentication.presentation.registration

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
import com.mk.tasky.core.presentation.TaskyTextField

@Composable
fun RegistrationScreen(
    onBackPress: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        Column {
            TaskyTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.OnNameChange(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(id = R.string.name),
                showError = state.nameError,
                isValid = state.validName
            )
            Spacer(modifier = Modifier.height(16.dp))
            TaskyEmailTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.OnEmailChange(it))
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
                    viewModel.onEvent(RegistrationEvent.OnPasswordChange(it))
                },
                onPasswordIconClick = {
                    viewModel.onEvent(RegistrationEvent.TogglePasswordVisibility)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(id = R.string.password),
                isTextHidden = state.isPasswordHidden,
                showError = state.passwordError
            )
            Spacer(modifier = Modifier.height(25.dp))
            TaskyButton(
                text = stringResource(id = R.string.get_started),
                onClick = { viewModel.onEvent(RegistrationEvent.Submit) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            TaskyButton(text = "<", onClick = onBackPress)
        }
    }
}
