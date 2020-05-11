package com.varma.hemanshu.firebasefunctions.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.varma.hemanshu.firebasefunctions.MainActivity
import com.varma.hemanshu.firebasefunctions.R

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Notification extension function.
 */
fun NotificationManager.triggerNotification(
    appContext: Context,
    channelId: String,
    title: String,
    message: String
) {

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
        .setContentTitle(title)
        .setContentText(message)
        .setAutoCancel(true)
        .setStyle(bigPicStyle)
        .setLargeIcon(androidLogo)
        .setContentIntent(contentPendingIntent)

    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 */
fun NotificationManager.cancelAllNotifications() {
    cancelAll()
}