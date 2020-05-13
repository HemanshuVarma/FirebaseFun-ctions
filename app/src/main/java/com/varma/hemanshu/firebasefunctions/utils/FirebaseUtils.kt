package com.varma.hemanshu.firebasefunctions.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Util class for Firebase Functions
 */
class FirebaseUtils {
    companion object {
        val TAG = "FirebaseUtil"

        /**
         * A method to subscribe to topic for FCM
         * @param topic Name fo topic to subscribe on
         */
        fun subscribeTopic(topic: String) {
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener { task ->
                    var message = "Success subscribing to $topic"
                    if (!task.isSuccessful) {
                        message = "Failed subscribing to $topic"
                    }
                    Log.i(TAG, message)
                }
        }
    }
}