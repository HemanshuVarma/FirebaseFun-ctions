package com.varma.hemanshu.firebasefunctions

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.varma.hemanshu.firebasefunctions.utils.triggerNotification

class MessagingService : FirebaseMessagingService() {

    /**
     * Called if InstanceID token is updated.
     * This may occur if the security of the previous token had been compromised.
     * @param token Retrieved from Firebase
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i(TAG, "New Token received $token")

        //TODO: Send the token to Server for easy retrieval
    }

    /**
     * Called when message is received.
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     * If app is in Foreground, Notification and payload is received here
     * If app is in Background, Notification in system tray and payload is received here in the "intent"
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i(TAG, "From: ${remoteMessage.from}")

        //App running in Foreground
        remoteMessage.notification?.let {
            Log.i(TAG, "Notification data payload: ${it.title}")
            Log.i(TAG, "Notification data payload: ${it.body}")
            Log.i(TAG, "Notification data payload: ${it.imageUrl}")

            val title = it.title
            val message = it.body
            val image = it.imageUrl

            sendNotification(title!!, message!!)
        }

        //App running in Background
        remoteMessage.data.isNotEmpty().let {
            Log.i(TAG, "Message data payload: ${remoteMessage.data}")
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param notificationTitle FCM message title received.
     * @param notificationMessage FCM message body received.
     */
    private fun sendNotification(notificationTitle: String, notificationMessage: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.triggerNotification(
            applicationContext,
            getString(R.string.fcm_notification_channel_id),
            notificationTitle,
            notificationMessage
        )
    }


    companion object {
        private const val TAG = "MessagingService"
    }
}