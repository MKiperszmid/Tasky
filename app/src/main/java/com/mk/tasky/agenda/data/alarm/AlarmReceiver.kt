package com.mk.tasky.agenda.data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.mk.tasky.R
import com.mk.tasky.core.navigation.DeepLinks
import com.mk.tasky.core.presentation.MainActivity
import com.mk.tasky.core.util.AgendaItemType

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.extras == null || context == null) return
        val extras = intent.extras!!

        val title = extras.getString(AlarmRegisterImpl.ITEM_TITLE) ?: return
        val description = extras.getString(AlarmRegisterImpl.ITEM_DESCRIPTION) ?: return
        val itemId = extras.getString(AlarmRegisterImpl.ITEM_ID) ?: return
        val itemTypeOrdinal = extras.getInt(AlarmRegisterImpl.ITEM_TYPE)

        val itemType = AgendaItemType.values()[itemTypeOrdinal]

        val channelId = "${itemType}_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(itemType, context, channelId)
        }
        showNotification(context, title, description, itemId, channelId, itemType)
    }

    private fun showNotification(
        context: Context,
        title: String,
        description: String,
        itemId: String,
        channelId: String,
        itemType: AgendaItemType
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.tasky_logo)
            .setContentIntent(getPendingIntent(context, itemId, itemType))
            .build()
        notificationManager.notify(1, notification)
    }

    private fun getPendingIntent(
        context: Context,
        itemId: String,
        itemType: AgendaItemType
    ): PendingIntent {
        val deeplink = when (itemType) {
            AgendaItemType.EVENT -> DeepLinks.EVENT_DETAIL + itemId
            AgendaItemType.REMINDER -> DeepLinks.REMINDER_DETAIL + itemId
            AgendaItemType.TASK -> DeepLinks.TASK_DETAIL + itemId
        }.toUri()
        val intent = Intent(
            Intent.ACTION_VIEW,
            deeplink,
            context,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1350, PendingIntent.FLAG_IMMUTABLE)
        }
    }

    private fun createNotificationChannel(
        itemType: AgendaItemType,
        context: Context,
        channelId: String
    ) {
        val channelName = "Reminders for ${itemType.text.asString(context)}"
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
