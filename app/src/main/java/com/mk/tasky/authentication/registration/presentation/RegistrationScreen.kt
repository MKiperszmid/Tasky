package com.mk.tasky.authentication.registration.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    registrationViewModel
    Text(text = "Register Screen")
}
