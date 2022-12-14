package com.mk.tasky.agenda.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.presentation.home.components.HomeAgendaItem
import com.mk.tasky.agenda.presentation.home.components.HomeDayPicker
import com.mk.tasky.agenda.presentation.home.components.HomeHeader
import com.mk.tasky.core.presentation.TaskyBackground
import com.mk.tasky.core.presentation.TaskyButton
import com.mk.tasky.core.presentation.TaskyDropdown
import com.mk.tasky.ui.theme.Green
import com.mk.tasky.ui.theme.Light
import com.mk.tasky.ui.theme.LightGreen
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    redirect: (HomeAgendaType, LocalDateTime) -> Unit,
    options: (HomeItemOptions, AgendaItem) -> Unit,
    onLogout: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.onEvent(HomeEvent.OnRefreshAgenda)
    }

    val isLoggedOut = state.isLoggedOut
    LaunchedEffect(key1 = isLoggedOut) {
        if (isLoggedOut) {
            onLogout()
        }
    }

    val datepickerState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = datepickerState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker { date ->
            viewModel.onEvent(HomeEvent.OnDateSelected(date))
        }
    }

    TaskyBackground(
        header = {
            HomeHeader(date = state.currentDate, name = state.profileName, onMonthClick = {
                datepickerState.show()
            }, onProfileClick = {
                    viewModel.onEvent(HomeEvent.OnLogoutClick)
                })
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                TaskyDropdown(
                    items = listOf("Logout"),
                    onItemSelected = {
                        onLogout()
                    },
                    showDropdown = state.showLogout,
                    onDismiss = { viewModel.onEvent(HomeEvent.OnLogoutDismiss) }
                )
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeDayPicker(date = state.currentDate, selectedDay = state.selectedDay, onDayClick = {
                viewModel.onEvent(HomeEvent.OnDayClick(it))
            })

            val date = state.currentDate.plusDays(state.selectedDay.toLong())
            val dateTitle = if (date == LocalDate.now()) {
                "Today"
            } else {
                val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM uuuu")
                date.format(dateFormatter)
            }
            Text(
                text = dateTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.agendaItems) {
                    HomeAgendaItem(
                        item = it,
                        onOptionsClick = {
                            viewModel.onEvent(HomeEvent.OnItemOptionsClick(it))
                        },
                        onItemClick = {
                            viewModel.onEvent(HomeEvent.OnItemClick(it))
                        },
                        color = when (it) {
                            is AgendaItem.Reminder -> Light
                            is AgendaItem.Task -> Green
                            is AgendaItem.Event -> LightGreen
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        val itemOptions = HomeItemOptions.values()
        val itemOptionNames = remember {
            itemOptions.map { it.type.asString(context) }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) { // TODO: Make it so alignment changes based on position in screen
            TaskyDropdown(
                items = itemOptionNames,
                onItemSelected = {
                    val selectedOption = itemOptions[it]
                    state.selectedAgendaItem?.let { item ->
                        if (selectedOption == HomeItemOptions.DELETE) {
                            viewModel.onEvent(HomeEvent.OnDeleteItem(item))
                        } else {
                            options(selectedOption, item)
                        }
                    }
                },
                onDismiss = { viewModel.onEvent(HomeEvent.OnItemOptionsDismiss) },
                showDropdown = state.showItemOptions
            )
        }

        val agendaTypeNames = remember {
            state.agendaTypes.map { it.name }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            TaskyButton(text = "+", onClick = {
                viewModel.onEvent(HomeEvent.OnAddAgendaClick)
            }, fontSize = 20.sp)
            TaskyDropdown(
                items = agendaTypeNames,
                onItemSelected = {
                    redirect(
                        state.agendaTypes[it],
                        LocalDateTime.of(
                            state.currentDate.plusDays(state.selectedDay.toLong()),
                            LocalTime.now()
                        )
                    )
                },
                onDismiss = { viewModel.onEvent(HomeEvent.OnAgendaItemDismiss) },
                showDropdown = state.showAgendaOptions
            )
        }
    }
}
