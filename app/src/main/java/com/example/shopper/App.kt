package com.example.shopper

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/**
 * This class creates a notification channel for shopper. Notification Channel became
 * necessary starting wiht Android Oreo API 26 to be able to show notifications. The
 * Notification channel for shopper will be created one time as soon as the appication
 * starts and will be available for all other activity classes to use.
 */
class App : Application() {
    // override the onCreate method
    override fun onCreate() {
        super.onCreate()

        // call method that creates Notification Channel for Shopper
        createNotificationChannel()
    }

    /**
     * This method creatse the notfication channel for shopper
     */
    private fun createNotificationChannel() {
        // check if Android Oreo(API 26) or higher becasue Nitification Channels werent available on lower versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // initialize Notification Chanel - must give it an Id, name and imporatnce
            val channelshopper = NotificationChannel(
                    CHANNEL_SHOPPER_ID,
                    "Channel Shopper",
                    NotificationManager.IMPORTANCE_DEFAULT
            )

            // customize the notification channel - set it sdescription
            channelshopper.description = "This is the Shopper Channel."

            // initialize a notification manager
            val manager = getSystemService(NotificationManager::class.java)

            // create shopper notification channel
            manager.createNotificationChannel(channelshopper)
        }
    }

    companion object {
        // declare and initialize a Channel id
        const val CHANNEL_SHOPPER_ID = "channelshopper"
    }
}