package com.varma.hemanshu.firebasefunctions.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import java.net.HttpURLConnection
import java.net.URL

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

        /**
         *To get a Bitmap image from the URL received
         */
        fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
            return try {
                Log.i(TAG, "Image to download: $imageUrl")
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}