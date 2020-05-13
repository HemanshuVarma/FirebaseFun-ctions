package com.varma.hemanshu.firebasefunctions

import android.util.Log
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

        /*
        //Parsed by Postman
        remoteMessage.let { message ->

            val title = message.data["title"]
            val description = message.data["body"]

            Log.i(TAG, "Notification data payload: $title")
            Log.i(TAG, "Notification data payload: $description")
            sendNotification(title!!, description!!)
        }
*/

        //App running in Foreground
        remoteMessage.notification?.let {
            Log.i(TAG, "Notification data payload: ${it.title}")
            Log.i(TAG, "Notification data payload: ${it.body}")
            Log.i(TAG, "Notification data payload: ${it.imageUrl}")
            Log.i(TAG, "Notification data payload: ${it.notificationPriority}")

            val title = it.title
            val message = it.body
            val image = it.imageUrl

            triggerNotification(
                applicationContext,
                getString(R.string.fcm_notification_channel_id),
                title!!,
                message!!
            )
        }

        //App running in Background
        remoteMessage.data.isNotEmpty().let {
            Log.i(TAG, "Message data payload: ${remoteMessage.data["title"]}")
        }
    }

    companion object {
        private const val TAG = "MessagingService"
    }
}