package com.mk.tasky.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mk.tasky.agenda.presentation.home.HomeAgendaType

@Composable
fun TaskyDropdown(
    items: List<String>,
    onItemSelected: (Int) -> Unit,
    showDropdown: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DropdownMenu(expanded = showDropdown, onDismissRequest = {
            onDismiss()
        }) {
            items.forEachIndexed { index, item ->
                TaskyDropdownItem(text = item, onClick = {
                    onItemSelected(index)
                    onDismiss()
                })
                if (index != items.size - 1) {
                    Divider()
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskyDropdownPreview() {
    TaskyDropdown(
        listOf(HomeAgendaType.Event.name, HomeAgendaType.Task.name, HomeAgendaType.Reminder.name),
        {},
        true,
        {}
    )
}
