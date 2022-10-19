package com.mk.tasky.agenda.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mk.tasky.agenda.domain.alarm.AlarmRegister
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.util.goAsync
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository: AgendaRepository

    @Inject
    lateinit var alarmRegister: AlarmRegister

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        val items = repository.getAllUpcomingItems().items
        items.forEach {
            alarmRegister.setAlarm(it)
        }
    }
}