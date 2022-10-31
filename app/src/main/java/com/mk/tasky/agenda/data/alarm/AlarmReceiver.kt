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
import com.mk.tasky.agenda.domain.model.AgendaItem
import com.mk.tasky.agenda.domain.repository.AgendaRepository
import com.mk.tasky.agenda.util.goAsync
import com.mk.tasky.core.navigation.DeepLinks
import com.mk.tasky.core.presentation.MainActivity
import com.mk.tasky.core.util.AgendaItemType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository: AgendaRepository
    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.extras == null || context == null) return@goAsync
        val extras = intent.extras!!

        val itemId = extras.getString(AlarmRegisterImpl.ITEM_ID) ?: return@goAsync
        val itemTypeString = extras.getString(AlarmRegisterImpl.ITEM_TYPE) ?: return@goAsync
        val itemType = AgendaItemType.valueOf(itemTypeString)

        val item = when (itemType) {
            AgendaItemType.EVENT -> repository.getEventById(itemId)
            AgendaItemType.REMINDER -> repository.getReminderById(itemId)
            AgendaItemType.TASK -> repository.getTaskById(itemId)
        }

        val channelId = "${itemType}_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(itemType, context, channelId)
        }
        showNotification(context, item, channelId)
    }

    private fun showNotification(
        context: Context,
        item: AgendaItem,
        channelId: String
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(item.title)
            .setContentText(item.description)
            .setSmallIcon(R.drawable.tasky_logo)
            .setContentIntent(getPendingIntent(context, item))
            .setAutoCancel(true)
            .build()
        notificationManager.notify(item.id.hashCode(), notification)
    }

    private fun getPendingIntent(
        context: Context,
        item: AgendaItem
    ): PendingIntent {
        // TODO: Handle all navigation using SealedClass so parameters aren't written over and over
        val deeplink = when (item) {
            is AgendaItem.Event -> DeepLinks.EVENT_DETAIL.replace("{id}", item.id)
            is AgendaItem.Reminder -> DeepLinks.REMINDER_DETAIL.replace("{id}", item.id)
            is AgendaItem.Task -> DeepLinks.TASK_DETAIL.replace("{id}", item.id)
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
        val channelName = "Reminder for ${itemType.text.asString(context)}"
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
