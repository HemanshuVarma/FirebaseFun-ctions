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

        //App running in Foreground. Check if message contains notification payload.
        remoteMessage.notification?.let {
            val title = it.title
            val message = it.body
            val image = it.imageUrl
            val priority = it.notificationPriority

            triggerNotification(
                applicationContext, getString(R.string.fcm_notification_channel_id),
                title!!, message!!
            )

            Log.i(TAG, "Notification title: $title")
            Log.i(TAG, "Notification message: $message")
            Log.i(TAG, "Notification image URL: $image")
            Log.i(TAG, "Notification priority: $priority")
        }

        //App running in Background. Check if message contains data payload.
        remoteMessage.data.isNotEmpty().let {
            Log.i(TAG, "Message data payload: ${remoteMessage.data}")
        }
    }

    companion object {
        private const val TAG = "MessagingService"
    }
}