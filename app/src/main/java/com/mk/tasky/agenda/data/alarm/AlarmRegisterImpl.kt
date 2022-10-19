package com.mk.tasky.agenda.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.mk.tasky.agenda.domain.alarm.AlarmRegister
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.util.toLong
import com.mk.tasky.core.util.AgendaItemType

class AlarmRegisterImpl(
    private val context: Context
) : AlarmRegister {
    companion object {
        const val ITEM_ID = "ITEM_ID"
        const val ITEM_TITLE = "ITEM_TITLE"
        const val ITEM_DESCRIPTION = "ITEM_DESCRIPITON"
        const val ITEM_TYPE = "ITEM_TYPE"
    }

    override fun updateAlarm(newItem: AgendaItem, previousItem: AgendaItem) {
        cancelAlarm(previousItem)
        setAlarm(newItem)
    }

    override fun cancelAlarm(item: AgendaItem) {
        val previousPendingIntent = getPendingIntent(item)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(previousPendingIntent)
    }

    override fun setAlarm(item: AgendaItem) {
        if (System.currentTimeMillis() >= item.remindAt.toLong()) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.remindAt.toLong(),
            getPendingIntent(item)
        )
    }

    private fun getPendingIntent(item: AgendaItem): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            10000,
            getIntent(item),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getIntent(item: AgendaItem): Intent {
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle().apply {
            putString(ITEM_TITLE, item.title)
            putString(ITEM_DESCRIPTION, item.description)
            putString(ITEM_ID, item.id)
            putString(ITEM_TYPE, AgendaItemType.fromAgendaItem(item).name)
        }
        return intent.apply {
            putExtras(bundle)
        }
    }
}
