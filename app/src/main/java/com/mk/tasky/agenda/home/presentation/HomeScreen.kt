package com.mk.tasky.agenda.home.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.agenda.home.presentation.components.HomeHeader
import com.mk.tasky.core.presentation.TaskyBackground

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    TaskyBackground(
        header = {
            HomeHeader(date = state.currentDate, name = state.profileName)
        }
    ) {
        Text(text = "Home Screen!")
    }
}
