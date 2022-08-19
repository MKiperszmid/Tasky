package com.mk.tasky.agenda.home.presentation

import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.agenda.home.presentation.components.HomeHeader
import com.mk.tasky.core.presentation.TaskyBackground

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    TaskyBackground(
        header = {
            HomeHeader(date = state.currentDate, name = state.profileName, onMonthClick = {
                Toast.makeText(context, "Clicked Month!", Toast.LENGTH_SHORT).show()
            }, onProfileClick = {
                    Toast.makeText(context, "Clicked Profile!", Toast.LENGTH_SHORT).show()
                })
        }
    ) {
        Text(text = "Home Screen!")
    }
}
