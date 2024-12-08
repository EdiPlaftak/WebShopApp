package hr.tvz.android.projektplaftak.model

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import hr.tvz.android.projektplaftak.R
import hr.tvz.android.projektplaftak.controller.MainActivity

class AppFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val handler = Handler(Looper.getMainLooper())

        handler.post{

            val notificationBuilder = Notification.Builder(this, resources.getString(R.string.appPromoChannel))

            notificationBuilder.setSmallIcon(R.drawable.cart)
            notificationBuilder.setContentTitle(message.notification?.title)
            notificationBuilder.setContentText(message.notification?.body)

            val resultIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(applicationContext, 1, resultIntent, PendingIntent.FLAG_IMMUTABLE)

            notificationBuilder.setContentIntent(pendingIntent)

            val notification = notificationBuilder.build()

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)
        }
    }

}