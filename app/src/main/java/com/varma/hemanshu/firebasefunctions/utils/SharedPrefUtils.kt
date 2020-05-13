package com.varma.hemanshu.firebasefunctions.utils

import android.content.Context
import com.varma.hemanshu.firebasefunctions.R

/**
 * Util class for Shared Preferences
 */
class SharedPrefUtils {

    companion object {
        private const val key = "pref_app"

        //Getter for First launch from Shared preferences
        fun getPrefData(context: Context) =
            context.getSharedPreferences(key, Context.MODE_PRIVATE).getBoolean(
                context.getString(
                    R.string.first_launch
                ), true
            )

        //Setter for First launch from Shared preferences
        fun setPrefData(context: Context) =
            context.getSharedPreferences(key, Context.MODE_PRIVATE).edit()
                .putBoolean(context.getString(R.string.first_launch), false).apply()
    }
}