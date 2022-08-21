package com.mk.tasky.agenda.home.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.agenda.home.presentation.components.HomeDayPicker
import com.mk.tasky.agenda.home.presentation.components.HomeDropdown
import com.mk.tasky.agenda.home.presentation.components.HomeHeader
import com.mk.tasky.core.presentation.TaskyBackground
import com.mk.tasky.core.presentation.TaskyButton

@Composable
fun HomeScreen(
    redirect: (HomeAgendaType) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = state.shouldRedirect) {
        if (state.selectedAgendaType == null || !state.shouldRedirect) return@LaunchedEffect
        redirect(state.selectedAgendaType)
        viewModel.onEvent(HomeEvent.OnRedirect)
    }

    TaskyBackground(
        header = {
            HomeHeader(date = state.currentDate, name = state.profileName, onMonthClick = {
                Toast.makeText(context, "Clicked Month!", Toast.LENGTH_SHORT).show()
            }, onProfileClick = {
                    Toast.makeText(context, "Clicked Profile!", Toast.LENGTH_SHORT).show()
                })
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeDayPicker(date = state.currentDate, selectedDay = state.selectedDay, onDayClick = {
                viewModel.onEvent(HomeEvent.OnDayClick(it))
            })
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            TaskyButton(text = "+", onClick = {
                viewModel.onEvent(HomeEvent.OnAddAgendaClick)
            }, fontSize = 20.sp)
            HomeDropdown(
                items = state.agendaTypes.map { it.name },
                onItemSelected = {
                    viewModel.onEvent(HomeEvent.OnAgendaItemSelected(state.agendaTypes[it]))
                },
                onDismiss = { viewModel.onEvent(HomeEvent.OnAgendaItemDismiss) },
                showDropdown = state.showAgendaOptions
            )
            HomeDropdown(
                items = state.reminderOptions.map { it.type },
                onItemSelected = {
                    viewModel.onEvent(HomeEvent.OnReminderItemSelected(state.reminderOptions[it]))
                },
                onDismiss = { viewModel.onEvent(HomeEvent.OnReminderItemDismiss) },
                showDropdown = state.showReminderOptions
            )
        }
    }
}
