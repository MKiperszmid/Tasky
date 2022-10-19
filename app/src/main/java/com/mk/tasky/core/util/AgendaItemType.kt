package com.mk.tasky.core.util

import androidx.annotation.IdRes
import com.mk.tasky.R
import com.mk.tasky.agenda.domain.model.AgendaItem

enum class AgendaItemType(val text: UIText) {
    EVENT(UIText.StringResource(R.string.event)),
    REMINDER(UIText.StringResource(R.string.reminder)),
    TASK(UIText.StringResource(R.string.task));

    companion object {
        fun fromAgendaItem(agendaItem: AgendaItem): AgendaItemType {
            return when (agendaItem) {
                is AgendaItem.Event -> EVENT
                is AgendaItem.Reminder -> REMINDER
                is AgendaItem.Task -> TASK
            }
        }

        fun fromId(@IdRes id: Int): AgendaItemType? {
            return when (id) {
                R.string.event -> EVENT
                R.string.reminder -> REMINDER
                R.string.task -> TASK
                else -> null
            }
        }
    }
}
