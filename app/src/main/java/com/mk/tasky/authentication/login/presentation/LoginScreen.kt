package com.mk.tasky.authentication.login.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    viewModel
    Text(text = "Login Screen!")
}
