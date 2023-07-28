package com.raflisalam.generasigigih.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.raflisalam.generasigigih.R
import com.raflisalam.generasigigih.ui.HomeActivity

object NotificationUtils {

    private const val CHANNEL_ID = "Flood_Channel_ID"
    private const val CHANNEL_NAME = "Flood_Channel"
    private const val NOTIFICATION_ID = 1

    fun checkAndSendPushNotification(context: Context, reportType: String, floodDepth: Int) {
        if (reportType == "flood" && floodDepth > 100) {
            showLocalNotificationModerate(context)
        } else {
            showLocalNotificationMinor(context)
        }
    }

    private fun showLocalNotificationMinor(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = Notification.Builder(context)
            .setSmallIcon(R.drawable.ic_banjir)
            .setContentTitle("Pemberitahuan Banjir")
            .setContentText("Anda berada dikawasan banjir dengan kedalaman 10 hingga 70 cm.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        val notification = builder.build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val defaultSoundUri = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION)
            notification.sound = defaultSoundUri
            notificationManager.notify(NOTIFICATION_ID, notification)
        } else {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }


    private fun showLocalNotificationModerate(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = Notification.Builder(context)
            .setSmallIcon(R.drawable.ic_banjir)
            .setContentTitle("Pemberitahuan Banjir")
            .setContentText("Hati-hati! Anda berada dikawasan banjir dengan kedalaman di atas 100cm")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        val notification = builder.build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val defaultSoundUri = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION)
            notification.sound = defaultSoundUri
            notificationManager.notify(NOTIFICATION_ID, notification)
        } else {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}