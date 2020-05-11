package com.varma.hemanshu.firebasefunctions.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.varma.hemanshu.firebasefunctions.R
import com.varma.hemanshu.firebasefunctions.utils.SharedPrefUtils
import com.varma.hemanshu.firebasefunctions.utils.cancelAllNotifications
import com.varma.hemanshu.firebasefunctions.utils.triggerNotification
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [HomeFragment] class.
 */
class HomeFragment : Fragment() {

    private lateinit var timer: CountDownTimer
    private val second: Long = 1_000L
    private val triggerTime: Long = 5_000L //5 seconds
    private val notificationTitle = "Hello Android"
    private val notificationMessage = "Welcome to Firebase Fun-ctions"
    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isFirstLaunch()

        //Click listener for Notify button
        notify_btn.setOnClickListener {
            activateTrigger()
        }
    }

    /**
     * Function to trigger notification after 5 seconds
     */
    private fun activateTrigger() {
        val notificationManager = ContextCompat.getSystemService(
            requireContext(),
            NotificationManager::class.java
        ) as NotificationManager

        Toast.makeText(context, getString(R.string.notify_message), Toast.LENGTH_SHORT).show()

        //Clearing all notifications
        notificationManager.cancelAllNotifications()

        //Countdown timer for 5 seconds
        timer = object : CountDownTimer(triggerTime, second) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                notificationManager.triggerNotification(
                    requireContext(),
                    getString(R.string.local_notification_channel_id),
                    notificationTitle,
                    notificationMessage
                )
            }
        }.start()
    }

    /**
     * Checks if it is first launch
     * if yes, then create channel(s) for notification
     */
    private fun isFirstLaunch() {
        val isFirstLaunch = SharedPrefUtils.getPrefData(requireContext())
        Log.i(TAG, "First Launch $isFirstLaunch")
        if (isFirstLaunch) {
            createChannel(
                requireContext(),
                getString(R.string.local_notification_channel_id),
                getString(R.string.local_notification_channel_name),
                getString(R.string.local_notification_channel_description)
            )
            SharedPrefUtils.setPrefData(requireContext())
        }
    }

    /**
     * Creates channel(s) for Android version Oreo, API 26 and above
     * Note: Any customisation here will require clean install of app to reflect changes
     */
    private fun createChannel(
        context: Context,
        channelId: String,
        channelName: String,
        channelDescription: String
    ) {
        //Channel is required for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    enableLights(true)
                    lightColor = Color.CYAN
                    enableVibration(true)
                    description = channelDescription
                    setShowBadge(true)
                }

            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}
