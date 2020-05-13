package com.varma.hemanshu.firebasefunctions.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.varma.hemanshu.firebasefunctions.MainActivity
import com.varma.hemanshu.firebasefunctions.R

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Function to trigger Notification.
 * @param appContext Application Context
 * @param channelId Channel to send notification to
 * @param title Title in notification
 * @param message Message/Description of Notification
 */
fun triggerNotification(
    appContext: Context,
    channelId: String,
    title: String,
    message: String
) {

    val notificationManager = ContextCompat.getSystemService(
        appContext,
        NotificationManager::class.java
    ) as NotificationManager

    // Activity/Fragment to open when pending intent is handled
    val contentIntent = Intent(appContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    //Pending Intent to handle click from notification panel
    val contentPendingIntent = PendingIntent.getActivity(
        appContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val androidLogo = BitmapFactory.decodeResource(
        appContext.resources,
        R.drawable.android_logo
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(androidLogo)
        .bigLargeIcon(null)

    // Setting properties for notification
    val builder = NotificationCompat.Builder(appContext, channelId)
        .setSmallIcon(R.drawable.ic_android)
        .setColor(appContext.resources.getColor(R.color.colorAndroidLogo))
        .setContentTitle(title)
        .setContentText(message)
        .setAutoCancel(true)
        .setStyle(bigPicStyle)
        .setLargeIcon(androidLogo)
        .setContentIntent(contentPendingIntent)

    notificationManager.notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 */
fun NotificationManager.cancelAllNotifications() {
    cancelAll()
}

/**
 * Cancels a specific notifications.
 * @param notificationId ID of the notification to cancel
 */
fun NotificationManager.cancelNotification(notificationId: Int) {
    cancel(notificationId)
}