package com.example.com.presentation.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.com.R
import kotlin.random.Random


class NotificationsMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.isNotEmpty().let { sendNotification(remoteMessage.data) }
    }

    override fun onNewToken(token: String) {
        //Add token to remote database
    }

    private fun sendNotification(message: MutableMap<String, String>) {
        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        applyDesign(notificationBuilder)
        applyIntent(notificationBuilder)
        applyConfig(notificationBuilder)
        applyContent(notificationBuilder, message)
        val notificationsManager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        notificationsManager.notify(Random.nextInt(500), notificationBuilder.build())
    }

    private fun applyDesign(builder: NotificationCompat.Builder) {
        builder.apply {
            color = ContextCompat.getColor(this@NotificationsMessagingService, R.color.colorPrimary)
            setSmallIcon(R.mipmap.ic_launcher_round)
        }
    }

    private fun applyIntent(builder: NotificationCompat.Builder) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
    }

    private fun applyConfig(builder: NotificationCompat.Builder) {
        builder.apply {
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(true)
            setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
            if (Build.VERSION.SDK_INT >= 21) setCategory(Notification.CATEGORY_SOCIAL)
        }
    }

    private fun applyContent(
        builder: NotificationCompat.Builder,
        data: Map<String, String>
    ) {
        builder.setContentTitle(data["title"]).setContentText(data["body"])
    }
}