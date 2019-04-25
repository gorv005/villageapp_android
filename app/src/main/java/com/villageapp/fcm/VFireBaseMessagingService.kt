package com.villageapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.villageapp.R
import com.villageapp.managers.PreferenceManager
import com.villageapp.ui.home.HomeActivity
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import com.villageapp.utils.LogUtils
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class VFireBaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val preferenceManager: PreferenceManager? by inject()

    override fun onMessageReceived(message: RemoteMessage?) {
        try {

            val notificationDataString: String? = message?.data?.get("data")
            var notificationMessage = message?.notification?.body
            var title = message?.notification?.title

            notificationMessage = if (notificationMessage.isNullOrBlank()) "Notification" else notificationMessage

            title = if (title.isNullOrBlank()) AndroidUtils.getString(R.string.app_name) else title

            createNotification(title, notificationMessage, notificationDataString)

        } catch (e: Exception) {
            LogUtils.e(e)
        }
    }


    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        LogUtils.d(null, "firebase token $token")

        preferenceManager?.run {
            savePreference(Config.SharedPreferences.PROPERTY_FCM_REGISTRATION_TOKEN, token)
            savePreference(Config.SharedPreferences.PROPERTY_IS_FCM_SENT_TO_SERVER, false)
        }
    }

    private fun createNotification(notificationTitle: String?, message: String, data: String?) {
        val intent = Intent(this, HomeActivity::class.java)

        if (data != null) {
            intent.putExtra("data", data)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(notificationTitle)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "name",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        notificationManager.notify(1, notificationBuilder.build())
    }

}