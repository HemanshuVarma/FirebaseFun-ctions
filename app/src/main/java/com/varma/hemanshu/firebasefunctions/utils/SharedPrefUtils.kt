package com.varma.hemanshu.firebasefunctions.utils

import android.content.Context
import com.varma.hemanshu.firebasefunctions.R

/**
 * Util class for Shared Preferences
 */
class SharedPrefUtils {

    companion object {
        private const val launchKey = "key_launch_count"
        private const val notificationIdKey = "key_notification_id"

        //Getter for First launch from Shared preferences
        fun getLaunchPrefData(context: Context) =
            context.getSharedPreferences(launchKey, Context.MODE_PRIVATE).getBoolean(
                context.getString(
                    R.string.first_launch
                ), true
            )

        //Setter for First launch from Shared preferences
        fun setLaunchPrefData(context: Context) =
            context.getSharedPreferences(launchKey, Context.MODE_PRIVATE).edit()
                .putBoolean(context.getString(R.string.first_launch), false).apply()

        //Getter for New Notification ID.
        fun getNextNotificationId(context: Context): Int {
            val sharedPreferences =
                context.getSharedPreferences(notificationIdKey, Context.MODE_PRIVATE)
            var id = sharedPreferences.getInt(
                context.getString(
                    R.string.notification_id
                ), 0
            ).plus(1)
            //Edge case for max value of Int, If so, then resetting
            if (id == Int.MAX_VALUE) id = 0
            sharedPreferences.edit().putInt(
                context.getString(
                    R.string.notification_id
                ), id
            ).apply()
            return id
        }

        //Getter for Current Notification ID.
        fun getCurrentNotificationId(context: Context): Int {
            return context.getSharedPreferences(notificationIdKey, Context.MODE_PRIVATE).getInt(
                context.getString(
                    R.string.notification_id
                ), 0
            )
        }
    }
}