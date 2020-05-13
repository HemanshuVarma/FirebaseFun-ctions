package com.varma.hemanshu.firebasefunctions.ui

import android.app.NotificationManager
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
import com.varma.hemanshu.firebasefunctions.utils.*
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
    private lateinit var notificationManager: NotificationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationManager = ContextCompat.getSystemService(
            requireContext(),
            NotificationManager::class.java
        ) as NotificationManager

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
        Toast.makeText(context, getString(R.string.notify_message), Toast.LENGTH_SHORT).show()

        //Clearing previous notification with same Id
        notificationManager.cancelNotification(
            SharedPrefUtils.getCurrentNotificationId(
                requireContext()
            )
        )

        //Countdown timer for 5 seconds
        timer = object : CountDownTimer(triggerTime, second) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                triggerNotification(
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
        val isFirstLaunch = SharedPrefUtils.getLaunchPrefData(requireContext())
        Log.i(TAG, "First Launch $isFirstLaunch")
        if (isFirstLaunch) {

            //Channel is required for Android Oreo and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //Local Channel
                CommonUtils.createChannel(
                    requireContext(),
                    getString(R.string.local_notification_channel_id),
                    getString(R.string.local_notification_channel_name),
                    getString(R.string.local_notification_channel_description),
                    NotificationManager.IMPORTANCE_HIGH
                )

                //FCM Channel with high importance
                CommonUtils.createChannel(
                    requireContext(),
                    getString(R.string.fcm_notification_channel_id),
                    getString(R.string.fcm_notification_channel_name),
                    getString(R.string.fcm_notification_channel_description),
                    NotificationManager.IMPORTANCE_HIGH
                )

                //Subscribe to specific topic in Firebase
                FirebaseUtils.subscribeTopic("development")
                FirebaseUtils.subscribeTopic("production")
            }
            SharedPrefUtils.setLaunchPrefData(requireContext())
        }
    }

    companion object {
        fun newInstance() = HomeFragment()

        private const val TAG = "HomeFragment"
    }
}
