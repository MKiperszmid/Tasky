package com.mk.tasky.agenda.domain.alarm

import com.mk.tasky.agenda.domain.model.AgendaItem

interface AlarmRegister {
    fun setAlarm(item: AgendaItem)
}